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

    int saveDayPlace(Long board_id);

    TripBoardAndCapital getBoardCapitalById(Long board_id);

    List<Place> getPlaceById(Long id);

    int modifyDayPlace(List<SelectPlace> place,Long board_id,Long dayid);

    List<Place> findPlaceByIds(List<SelectPlace> place);

    int saveNewPlace(Place place);

}
