package com.gaerine.triple.service.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaerine.triple.domain.board.*;
import com.gaerine.triple.domain.member.Member;
import com.gaerine.triple.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Slf4j
class BoardServiceTest {

//    @Mock
    private BoardMapper mapper;

//    @InjectMocks
    @Autowired private BoardServiceImpl service;
    @Autowired private TestService service2;

    @Test
    void getWorld() {
        // given
        List<World> worlds = Arrays.asList(new World(new Long(1),"아시아"), new World(new Long(2),"유럽"));
        Mockito.when(mapper.selectWorld()).thenReturn(worlds);

        // when
        List<World> world = service.getWorld();
        log.info("world={}",world);

        // then
        Assertions.assertThat(world).isNotNull().isNotEmpty();
        Assertions.assertThat(world).contains(worlds.get(0)).contains(worlds.get(1));
    }

    @Test
    void getCapital() {
        List<Capital> capital = service.getCapital();
        log.info("capital={}",capital);
    }

    @Test
    void getDays() {
        TripBoard test = service.getBoardByBoardId(new Long(11));
        LocalDate start_date = test.getStart_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end_date = test.getEnd_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long days = ChronoUnit.DAYS.between(start_date, end_date);
        log.info("test={}",days);
    }

    @Test
    void getPlaces(){
        List<SelectPlace> places = new ArrayList<>();
        places.add(new SelectPlace(){{
            setPlaceId(1L);
            setPlaceName("살라 다낭 비치 호텔");
        }});
        places.add(new SelectPlace() {{
            setPlaceId(6L);
            setPlaceName("살라우키키");
        }});

        List<Long> list = places.stream().map(id -> id.getPlaceId()).collect(Collectors.toList());

        log.info("places={}",list);
    }

    @Test
    void getPlacewithPlan() throws JsonProcessingException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Optional<TripBoardAndCapital> board = Optional.ofNullable(service.getBoardCapitalById(16L));
        List<Place> place = service.getPlaceById(board.get().getTripBoard().getCapital());
        DayPlace plan = service.getDayPlaceByBoardId(16L);

        Long period = board.get().getTripBoard().getPeriod();
        Map<Integer,List<Place>> resultPlaces = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for(int day = 1; day<=period; day++) {
            String methodName = "getDay" + day;
            Method method = plan.getClass().getMethod(methodName);

            String invoke = (String) method.invoke(plan);
            log.info("invoke={}",invoke);
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

        log.info("result={}",resultPlaces);
    }



    @Test
    void testRouting(){
        Member local = service2.testLocal();
        Member cloude = service2.testClude();
        log.info("local ={}",local);
        log.info("cloude ={}",cloude);
    }


}