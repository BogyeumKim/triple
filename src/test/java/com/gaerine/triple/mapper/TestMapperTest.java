package com.gaerine.triple.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class TestMapperTest {

    @Autowired TestMapper mapper;

    @Test
    public void getTime(){
        log.info(mapper.getTime());
    }

    @Test
    public void getTime2(){
        log.info(mapper.getTime2());
    }
}