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

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Member> login(@RequestBody LoginVO login) {
        log.info("login={}", login);
        Optional<Member> loginMember = memberService.login(login);

        HttpHeaders headers = null;
        if (loginMember.isPresent()) {
            Token token = tokenService.saveToken(loginMember.get().getMember_id());
            headers = new HttpHeaders();
            headers.setBearerAuth(token.getAccess_token());
        }
        return loginMember.isPresent() ? new ResponseEntity<>(headers, HttpStatus.OK) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
