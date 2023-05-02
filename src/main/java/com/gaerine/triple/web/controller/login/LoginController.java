package com.gaerine.triple.web.controller.login;


import com.gaerine.triple.domain.login.LoginVO;
import com.gaerine.triple.domain.member.Member;
import com.gaerine.triple.service.member.MemberService;
import com.gaerine.triple.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class LoginController {

    private final MemberService service;

    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody LoginVO login, HttpServletRequest req){
        log.info("login={}",login);
        Optional<Member> loginMember = service.login(login);

        if(loginMember.isPresent()){
            HttpSession session = req.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);
            log.info("session={}",session.getAttribute(SessionConst.LOGIN_MEMBER));
        }
        return loginMember.isPresent() ? new ResponseEntity<>(loginMember.get(), HttpStatus.OK) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
