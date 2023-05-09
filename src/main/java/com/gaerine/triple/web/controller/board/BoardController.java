package com.gaerine.triple.web.controller.board;

import com.gaerine.triple.domain.board.Capital;
import com.gaerine.triple.domain.board.World;
import com.gaerine.triple.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> searchWorld(@RequestBody Optional<String> capital){
        if(capital.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        List<Capital> result = service.getCapitalByInput(capital.get());
        if(result != null){
            return ResponseEntity.ok().body(result);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("없는 도시");
        }
    }



}
