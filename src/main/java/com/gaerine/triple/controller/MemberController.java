package com.gaerine.triple.controller;

import com.gaerine.triple.domain.member.Member;
import com.gaerine.triple.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;

    // register.html 이동
    @GetMapping("/register")
    public String moveReg(){
        log.info("move...");
        return "member/register";
    }

    // 멤버 가입
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<String> signUp(@RequestBody Member member){
        Member newMember = service.register(member);
        log.info("newMember={}",newMember);
        return newMember.getMember_id() !=0 ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
