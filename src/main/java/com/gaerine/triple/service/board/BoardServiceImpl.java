package com.gaerine.triple.service.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaerine.triple.domain.board.*;
import com.gaerine.triple.exception.PlaceDupleException;
import com.gaerine.triple.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
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
    public int modifyDayPlace(List<SelectPlace> place,Long board_id, Long dayid) {
        ObjectMapper objectMapper = new ObjectMapper();
        int result = 0;
        try {
            // 기존 dayplace 조회
            DayPlace dayPlace = mapper.selectDayPlaceByBoardId(board_id);

            // 메소드 동적 호출
            String getMethodDay = "getDay" + dayid;
            Method method = dayPlace.getClass().getMethod(getMethodDay);
            String getMethod = (String) method.invoke(dayPlace);

            if(getMethod == null){
                String insertPlace = objectMapper.writeValueAsString(place);
                result = mapper.updateDayPlace(insertPlace, board_id, dayid);
            }else{
                // 동적 호출한 메소드 SelectPlace List로 타입 저장
                List<SelectPlace> oldPlace = objectMapper.readValue(getMethod, new TypeReference<List<SelectPlace>>() {});

                // 기존 dayPlace와 새로 선택한 place 합침 when
                List<SelectPlace> addPlace = new ArrayList<>();
                addPlace.addAll(oldPlace);
                addPlace.addAll(place);

                String list = objectMapper.writeValueAsString(addPlace);

                result = mapper.updateDayPlace(list, board_id,dayid);

            }

            if(result !=0) {
                return 1;
            }

        } catch (JsonProcessingException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
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

    @Override
    public int saveNewPlace(Place place) {
        return mapper.insertPlace(place);
    }

    @Override
    public Place modifyNewDayPlace(Place place, Long board_id, Long dayid) throws JsonProcessingException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        int count = mapper.selectPlaceByKoreaName(place.getKorea_name());
        if(count>0){
            throw new PlaceDupleException();
        }

        else {
            int inserPlace = mapper.insertPlace(place);
            if(inserPlace !=0 ){
                Optional<DayPlace> dayPlace = Optional.ofNullable(mapper.selectDayPlaceByIdDay(board_id, dayid));

                List<SelectPlace> selectPlaces = new ArrayList<>();
                ObjectMapper objectMapper = new ObjectMapper();

                SelectPlace newPlace = new SelectPlace();
                newPlace.setPlaceId(place.getId());
                newPlace.setPlaceName(place.getKorea_name());
                if (dayPlace.isPresent()) {

                    String StringMethod = "getDay" + dayid;
                    Method method = dayPlace.get().getClass().getMethod(StringMethod);
                    String result = (String)method.invoke(dayPlace.get());
                    log.info("invoke={}",result);
                    if(result != null){
                        selectPlaces = objectMapper.readValue(result, new TypeReference<List<SelectPlace>>() {});

                    }
                }

                selectPlaces.add(newPlace);
                log.info("SelectPlaces={}",selectPlaces);
                String list = objectMapper.writeValueAsString(selectPlaces);
                mapper.updateDayPlace(list,board_id,dayid);
                return place;
            }
            return null;
        }
    }

    @Override
    public Map<Integer, List<Place>> userPlaces(Optional<TripBoardAndCapital> board, List<Place> place, DayPlace plan) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, JsonProcessingException {
        Long period = board.get().getTripBoard().getPeriod();
        Map<Integer,List<Place>> resultPlaces = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for(int day = 1; day<=period; day++) {
            String methodName = "getDay" + day;
            Method method = plan.getClass().getMethod(methodName);

            String invoke = (String) method.invoke(plan);
            if(invoke == null){
                continue;
            }

            List<SelectPlace> planData = objectMapper.readValue(invoke, new TypeReference<List<SelectPlace>>() {});
            List<Place> matchedPlaces = new ArrayList<>();

            for (SelectPlace item : planData) {
                Long id = item.getPlaceId();

                for (Place placeItem : place) {
                    if (placeItem.getId() == id) {
                        matchedPlaces.add(placeItem);
                        break;
                    }
                }

            }
            resultPlaces.put(day, matchedPlaces);
        }
        return resultPlaces;
    }
}
