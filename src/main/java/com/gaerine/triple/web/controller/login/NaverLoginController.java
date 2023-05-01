package com.gaerine.triple.web.controller.login;


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

        Member socialMember = service.socialRegister(getNaverUser);
        log.info("socialMember={}",socialMember);

        if(getNaverData.get("message").equals("success")){

            String closePu;
            // 신규가입
            if(socialMember.getMember_id() != null) {
                closePu ="<script>window.opener.postMessage('naverRegSuccess','*');</script>";
            }
            // 기존 회원
            else {
                closePu ="<script>window.opener.postMessage('naverLoginSuccess','*');</script>";
            }

            response.getWriter().write(closePu);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else{
            String closePu ="<script>window.opener.postMessage('naverLoginFail','*');</script>";
            response.getWriter().write(closePu);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
