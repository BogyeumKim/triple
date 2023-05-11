package com.gaerine.triple.service.board;

import com.gaerine.triple.domain.board.Capital;
import com.gaerine.triple.domain.board.TripBoard;
import com.gaerine.triple.domain.board.World;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Slf4j
class BoardServiceTest {

//    @Mock
    private BoardMapper mapper;

//    @InjectMocks
    @Autowired private BoardServiceImpl service;

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

}