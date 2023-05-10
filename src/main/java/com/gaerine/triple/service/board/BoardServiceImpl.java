package com.gaerine.triple.service.board;

import com.gaerine.triple.domain.board.Capital;
import com.gaerine.triple.domain.board.DayPlace;
import com.gaerine.triple.domain.board.TripBoard;
import com.gaerine.triple.domain.board.World;
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
/*        Date startDate = data.getStart_date();
        Date endDate = data.getEnd_date();

        SimpleDateFormat smft= new SimpleDateFormat("yyyy-MM-dd");
        String stringStart = smft.format(startDate);
        String stringEnd = smft.format(endDate);

        try {
            Date formatStart = smft.parse(stringStart);
            Date foramtEnd = smft.parse(stringEnd);
            data.setStart_date(formatStart);
            data.setEnd_date(foramtEnd);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }*/
        int result = mapper.insertTripBoard(data);
        if(result == 1){
            return data;
        }else{
            return null;
        }

    }
}
