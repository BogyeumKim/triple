package com.gaerine.triple.web.controller.board;

import com.gaerine.triple.domain.board.Capital;
import com.gaerine.triple.domain.board.World;
import com.gaerine.triple.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @GetMapping("/write")
    public String moveFindWolrd(Model model){
        List<World> world = service.getWorld();
        List<Capital> capital = service.getCapital();
        model.addAttribute("world",world);
        model.addAttribute("capital",capital);
        return "board/findWorld";
    }

    @PostMapping("/reqSearchAPI")
    public ResponseEntity<?> searchWorld(@RequestBody String input){
        log.info("input={}",input);

    }



}
