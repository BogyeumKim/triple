package com.gaerine.triple.mapper;

import com.gaerine.triple.domain.board.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


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

}