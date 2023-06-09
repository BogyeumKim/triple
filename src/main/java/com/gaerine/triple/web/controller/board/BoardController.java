package com.gaerine.triple.web.controller.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaerine.triple.ApiKey;
import com.gaerine.triple.domain.board.*;
import com.gaerine.triple.service.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
public class BoardController {

    private final BoardService service;
    private final ApiKey apiKey;

    @Autowired
    public BoardController(BoardService service, ApiKey apiKey) {
        this.service = service;
        this.apiKey = apiKey;
    }

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

    @PostMapping("/reqChoiceDay")
    public ResponseEntity<TripBoard> moveNextStep(@RequestBody TripBoard data){
        if(data.getCapital() == null || data.getLocation() == null || (data.getStart_date() == null || data.getEnd_date() == null) ){
            return ResponseEntity.badRequest().build();
        }

        TripBoard tripBoard = service.saveBoard(data);
        if(tripBoard.getBoard_id() != null ){
            service.saveDayPlace(tripBoard.getBoard_id());
        }
        return tripBoard.getBoard_id() != null ? ResponseEntity.ok().body(tripBoard) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/board/{boardId}")
    public String readBoard(@PathVariable Long boardId,Model model){
        Optional<TripBoardAndCapital> board = Optional.ofNullable(service.getBoardCapitalById(boardId));

        if(board.isEmpty()) {
            // 에러 view 만들어서 리턴시키기 , 로그인 검증도 추가하자)
            return null;
        }
        List<Place> place = service.getPlaceById(board.get().getTripBoard().getCapital());
        DayPlace plan = service.getDayPlaceByBoardId(boardId);

        model.addAttribute("board",board.get())
                .addAttribute("key",apiKey.getApiKey())
                .addAttribute("place",place)
                .addAttribute("plan",plan);

        return "board/map";
    }

    @PostMapping("/reqPlace/{boardId}")
    public ResponseEntity<List<Place>> savePlace(@RequestParam("dayId") Long dayId,@RequestBody List<SelectPlace> data,@PathVariable("boardId") Long boardId){
        if(data.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        int modify = service.modifyDayPlace(data, boardId,dayId);
        List<Place> places = service.findPlaceByIds(data);
        return modify == 1 ? ResponseEntity.ok().body(places) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/newPlace/{boardId}")
    public ResponseEntity<?> saveNewPlace(@RequestParam("dayId") Long dayId, @Valid @RequestBody Place data, @PathVariable("boardId") Long boardId) throws JsonProcessingException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Optional<Place> place = Optional.ofNullable(service.modifyNewDayPlace(data, boardId, dayId));
        if(place.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(place);
    }

}
