package com.gaerine.triple.web.controller.register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
@Slf4j
public class RegisterController {

    // register.html 이동
    @GetMapping("/member/register")
    public String moveReg(Model model){
        String state = UUID.randomUUID().toString();
        model.addAttribute("state",state);
        return "member/register";
    }
}
