package com.gaerine.triple.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaerine.triple.domain.member.Member;
import com.gaerine.triple.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class NaverLoginController {

    private final MemberService service;

    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<String> callback(@RequestParam Map<String, String> val, HttpServletResponse response) throws IOException {
        log.info("val={}",val);


        WebClient webToken = WebClient.builder().baseUrl("https://nid.naver.com/oauth2.0/token").build();

        Map<String,String> getToken = webToken.get().uri(uriBuilder -> uriBuilder
                        .queryParam("grant_type","authorization_code")
                        .queryParam("client_id","nPHj7peh5QV2gkYPN7Xv")
                        .queryParam("client_secret","gsnF7eKWus")
                .queryParam("code",val.get("code"))
                .queryParam("state",val.get("state"))
                .build()
        ).retrieve().bodyToMono(Map.class).block();
        log.info("Token={}",getToken);

        WebClient getAcc = WebClient.builder().baseUrl("https://openapi.naver.com/v1/nid/me")
                .defaultHeader(HttpHeaders.AUTHORIZATION,"Bearer "+getToken.get("access_token"))
                .build();

        Map<String,Object> getNaverData = getAcc.get().uri(uriBuilder -> uriBuilder.build()).retrieve().bodyToMono(Map.class).block();
        log.info("Data={}",getNaverData);

        Object responseObj = getNaverData.get("response");
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> getNaverUser = mapper.convertValue(responseObj, Map.class);

        Member member = new Member();
        member.setUser_id(getNaverUser.get("email"));
        member.setUser_pw("");
        member.setUser_nick(getNaverUser.get("nickname"));
        member.setSocial_id(getNaverUser.get("id"));

        service.socialRegister(member);

        if(getNaverData.get("message").equals("success")){
            String closePu ="<script>window.opener.postMessage('naverLoginSuccess','*');</script>";
            response.getWriter().write(closePu);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
