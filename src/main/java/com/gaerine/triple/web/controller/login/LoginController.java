package com.gaerine.triple.web.controller.login;


import com.gaerine.triple.domain.login.LoginVO;
import com.gaerine.triple.domain.member.Member;
import com.gaerine.triple.domain.token.Token;
import com.gaerine.triple.service.member.MemberService;
import com.gaerine.triple.service.token.TokenService;
import com.gaerine.triple.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@Slf4j
public class LoginController {

    private final MemberService memberService;
    private final TokenService tokenService;

    @Autowired
    public LoginController(MemberService memberService, TokenService tokenService) {
        this.memberService = memberService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody LoginVO login, HttpServletResponse response) {
        log.info("login={}", login);
        Optional<Member> loginMember = memberService.login(login);

        HttpHeaders headers = null;
        if (loginMember.isPresent()) {
            Token token = null;
            Token tokenById = tokenService.getTokenById(loginMember.get().getMember_id());

            if(tokenById == null){
                token = tokenService.saveToken(loginMember.get().getMember_id()); // token 없으면 새로 저장
            }else{
                token = tokenService.getLoginToken(tokenById.getAccess_token()); // 기존에 token이 있고 만료시간 지나도 알아서 갱신
            }

            headers = new HttpHeaders();

            if(token != null){
                headers.setBearerAuth(token.getAccess_token());
                Cookie cookie = new Cookie("refreshToken",token.getRefresh_token());
                cookie.setMaxAge(30 * 24 * 60 * 60); // 리프레쉬 토큰 유효기간 30일
                cookie.setHttpOnly(true);
                cookie.setPath("/");

                Cookie cookie2 = new Cookie("Authorization",token.getAccess_token());
                cookie2.setMaxAge(7 * 24 * 60 * 60);
                cookie2.setHttpOnly(true);
                cookie2.setPath("/");

                response.addCookie(cookie);
                response.addCookie(cookie2);
            }
        }

        return loginMember.isPresent() ? new ResponseEntity<>(headers, HttpStatus.OK) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
