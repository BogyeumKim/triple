package com.gaerine.triple.web.controller.test;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class TestController {

    @GetMapping("/testGetList")
    public ResponseEntity<List<String>> resTestList(){
        String[] list = {"가","나","다","라"};
        List<String> test = Arrays.asList(list);
        return ResponseEntity.ok().body(test);
    }
}
