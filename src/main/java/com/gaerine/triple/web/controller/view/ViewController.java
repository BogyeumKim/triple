package com.gaerine.triple.web.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
@Slf4j
public class ViewController {

    @GetMapping("/")
    public String main(){
        return "index";
    }

    // register.html 이동
    @GetMapping("/member/register")
    public String moveReg(Model model){
        String state = UUID.randomUUID().toString();
        model.addAttribute("state",state);
        return "member/register";
    }

    // map 이동
    @GetMapping("/map")
    public String moveBoard(){
        return "board/map";
    }
}
