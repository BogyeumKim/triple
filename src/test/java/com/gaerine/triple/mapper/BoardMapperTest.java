package com.gaerine.triple.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaerine.triple.domain.board.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
class BoardMapperTest {

    @Autowired BoardMapper mapper;

    @Test
    public void getWorld(){
        List<World> list = mapper.selectWorld();
        log.info("World={}",list);
        Assertions.assertThat(list).isNotNull().isNotEmpty();
    }
    @Test
    public void getCapital(){
        List<Capital> list = mapper.selectCapital();
        log.info("list={}",list);
        Assertions.assertThat(list).isNotNull().isNotEmpty();
    }

    @Test
    public  void getBoard(){
        TripBoard userBoard = mapper.selectBoardByBoardId(new Long(1));
        log.info("userBoard={}",userBoard);
        Assertions.assertThat(userBoard).isNotNull();
    }
    @Test
    public void getDayPlace(){
        DayPlace dayPlace = mapper.selectDayPlaceByBoardId(new Long(1));
        log.info("dayPlace={}",dayPlace);
        Assertions.assertThat(dayPlace).isNotNull();
    }

    @Test
    public void getCapitalByLike(){
        List<Capital> result = mapper.selectCapitalByLikeInput("호");
        log.info("result={}",result);
    }

    @Test
    public void getPlaceByCapital(){
        List<Place> place = mapper.selectPlaceByCapital(new Long(5));
        log.info("place={}",place);
        Assertions.assertThat(place).isNotNull();
    }

    @Test
    void selectPlaceByIds() {
        List<Long> list = Arrays.asList(1L,2L,3L);
        List<Place> places = mapper.selectPlaceByIds(list);
        log.info("palce={}",places);

        Assertions.assertThat(places).isNotEmpty().isNotNull();
    }

    @Test
    void selectDayPlaceByIdDay() throws JsonProcessingException {
        Optional<DayPlace> dayPlace = Optional.ofNullable(mapper.selectDayPlaceByIdDay(16L, 1L));
        if(dayPlace.isPresent()){
            ObjectMapper objectMapper = new ObjectMapper();
            String result = dayPlace.get().getDay1();

            List<SelectPlace> selectPlaces = objectMapper.readValue(result, new TypeReference<List<SelectPlace>>() {});

            Place pa = new Place();
            pa.setId(14L);
            pa.setCapital_id(5L);
            pa.setKorea_name("테스트");
            pa.setCategory("테스트카테고리");
            pa.setLat(123.123123D);
            pa.setLng(123.123123D);

            SelectPlace test = new SelectPlace();
            test.setPlaceId(pa.getId());
            test.setPlaceName(pa.getKorea_name());

            selectPlaces.addAll(Collections.singleton(test));

            log.info("result={}",selectPlaces);

        }

    }
}