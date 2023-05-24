package com.gaerine.triple.web.controller.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaerine.triple.ApiKey;
import com.gaerine.triple.domain.board.DayPlace;
import com.gaerine.triple.domain.board.Place;
import com.gaerine.triple.domain.board.TripBoardAndCapital;
import com.gaerine.triple.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
public class ViewController {

    private final BoardService service;
    private final ApiKey apiKey;

    @Autowired
    public ViewController(BoardService service, ApiKey apiKey) {
        this.service = service;
        this.apiKey = apiKey;
    }

    @GetMapping("/")
    public String main(Model model) throws JsonProcessingException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Optional<TripBoardAndCapital> board = Optional.ofNullable(service.getBoardCapitalById(16L));

        if(board.isEmpty()) {
            // 에러 view 만들어서 리턴시키기 , 로그인 검증도 추가하자)
            return null;
        }
        List<Place> place = service.getPlaceById(board.get().getTripBoard().getCapital());
        DayPlace plan = service.getDayPlaceByBoardId(16L);
        Map<Integer, List<Place>> userPlaces = service.userPlaces(board, place, plan);


        model.addAttribute("board",board.get())
                .addAttribute("key",apiKey.getApiKey())
                .addAttribute("place",place)
                .addAttribute("plan",plan)
                .addAttribute("places",userPlaces);
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
