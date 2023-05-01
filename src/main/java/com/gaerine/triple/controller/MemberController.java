package com.gaerine.triple.controller;

import com.gaerine.triple.domain.member.Member;
import com.gaerine.triple.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;

    // register.html 이동
    @GetMapping("/register")
    public String moveReg(Model model){
        String state = UUID.randomUUID().toString();
        model.addAttribute("state",state);
        return "member/register";
    }

    // 멤버 가입
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<String> signUp(@RequestBody Member member){
        Member newMember = service.register(member);
        log.info("newMember={}",newMember);
        return newMember.getMember_id() != null ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
