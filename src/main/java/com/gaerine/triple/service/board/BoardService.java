package com.gaerine.triple.service.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaerine.triple.domain.board.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BoardService {

    List<World> getWorld();

    List<Capital> getCapital();

    TripBoard getBoardByBoardId(Long board_id);

    DayPlace getDayPlaceByBoardId(Long board_id);

    List<Capital> getCapitalByInput(String input);

    TripBoard saveBoard(TripBoard data);

    int saveDayPlace(TripBoard board_id);

    TripBoardAndCapital getBoardCapitalById(Long board_id);

    List<Place> getPlaceById(Long id);

    int modifyDayPlace(List<SelectPlace> place,Long board_id,Long dayid);

    List<Place> findPlaceByIds(List<SelectPlace> place);

    int saveNewPlace(Place place);

    Place modifyNewDayPlace(Place place, Long board_id, Long dayid) throws JsonProcessingException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    Map<Integer,List<Place>> userPlaces(Optional<TripBoardAndCapital> board, List<Place> place, DayPlace plan) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, JsonProcessingException;
}
