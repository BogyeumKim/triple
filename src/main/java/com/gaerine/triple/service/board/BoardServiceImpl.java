package com.gaerine.triple.service.board;

import com.gaerine.triple.domain.board.Capital;
import com.gaerine.triple.domain.board.DayPlace;
import com.gaerine.triple.domain.board.TripBoard;
import com.gaerine.triple.domain.board.World;
import com.gaerine.triple.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public TripBoard getBoardByUserId(String user_id) {
        return mapper.selectBoardByUserId(user_id);
    }

    @Override
    public DayPlace getDayPlaceByBoardId(Long board_id) {
        return mapper.selectDayPlaceByBoardId(board_id);
    }
}
