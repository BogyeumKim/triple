package com.gaerine.triple.service.board;

import com.gaerine.triple.domain.board.*;

import java.util.List;

public interface BoardService {

    List<World> getWorld();

    List<Capital> getCapital();

    TripBoard getBoardByBoardId(Long board_id);

    DayPlace getDayPlaceByBoardId(Long board_id);

    List<Capital> getCapitalByInput(String input);

    TripBoard saveBoard(TripBoard data);

    TripBoardAndCapital getBoardCapitalById(Long board_id);
}
