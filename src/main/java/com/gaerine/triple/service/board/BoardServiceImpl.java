package com.gaerine.triple.service.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaerine.triple.domain.board.*;
import com.gaerine.triple.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper mapper;

    @Override
    public List<World> getWorld() {
        return mapper.selectWorld();
    }

    @Override
    public List<Capital> getCapital() {
        return mapper.selectCapital();
    }

    @Override
    public TripBoard getBoardByBoardId(Long board_id) {
        return mapper.selectBoardByBoardId(board_id);
    }

    @Override
    public DayPlace getDayPlaceByBoardId(Long board_id) {
        return mapper.selectDayPlaceByBoardId(board_id);
    }

    @Override
    public List<Capital> getCapitalByInput(String input) {
        return mapper.selectCapitalByLikeInput(input);
    }

    @Override
    public TripBoard saveBoard(TripBoard data) {
        int result = mapper.insertTripBoard(data);
        return result ==1 ? data : null;
    }

    @Override
    public int saveDayPlace(Long board_id) {
        return mapper.insertDayPlace(board_id);
    }

    @Override
    public TripBoardAndCapital getBoardCapitalById(Long board_id) {
        Optional<TripBoard> board = Optional.ofNullable(mapper.selectBoardByBoardId(board_id));
        if(board.isEmpty()){
            return null;
        }
        Capital capital = mapper.selectCapitalById(board.get().getCapital());
        TripBoardAndCapital result = new TripBoardAndCapital();
        result.setTripBoard(board.get());
        result.setCapital(capital);
        return result;
    }

    @Override
    public List<Place> getPlaceById(Long id) {
        return mapper.selectPlaceByCapital(id);
    }

    @Override
    public int modifyDayPlace(List<SelectPlace> place,Long board_id) {
        ObjectMapper objectMapper = new ObjectMapper();
        int result = 0;
        try {
            String list = objectMapper.writeValueAsString(place);
            result = mapper.updateDayPlace(list, board_id);

            if(result !=0) {
                return 1;
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    @Override
    public List<Place> findPlaceByIds(List<SelectPlace> place) {
        List<Long> list = place.stream().map(id -> id.getPlaceId()).collect(Collectors.toList());
        List<Place> result = mapper.selectPlaceByIds(list);
        return result;
    }
}
