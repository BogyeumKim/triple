package com.gaerine.triple.service.board;

import com.gaerine.triple.domain.board.Capital;
import com.gaerine.triple.domain.board.DayPlace;
import com.gaerine.triple.domain.board.TripBoard;
import com.gaerine.triple.domain.board.World;

import java.util.List;

public interface BoardService {

    List<World> getWorld();

    List<Capital> getCapital();

    TripBoard getBoardByUserId(String user_id);

    DayPlace getDayPlaceByBoardId(Long board_id);

    List<Capital> getCapitalByInput(String input);

}
