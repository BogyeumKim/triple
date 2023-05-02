package com.gaerine.triple.web.controller.register;

import com.gaerine.triple.domain.member.Member;
import com.gaerine.triple.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
public class RegisterController {

    // register.html 이동
    @GetMapping("/member/register")
    public String moveReg(Model model, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) Optional<Member> loginMember){
        String state = UUID.randomUUID().toString();
        model.addAttribute("state",state);

        log.info("SessionMember={}",loginMember);
        if(loginMember.isPresent()){
            model.addAttribute("member",loginMember);
        }
        return "member/register";
    }
}
