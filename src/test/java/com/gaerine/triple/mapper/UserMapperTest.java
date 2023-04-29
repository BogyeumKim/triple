package com.gaerine.triple.mapper;

import com.gaerine.triple.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class UserMapperTest {

    @Autowired UserMapper mapper;

    @Test
    @Transactional
    void save() {
        Member mb = new Member();
        mb.setUser_id("test01");
        mb.setUser_pw("123123");
        mb.setUser_nick("테스터");

        mapper.save(mb);
        log.info("mb={}",mb);
    }


    @Test
    void getMember() {
        Long mbId=1L;
        log.info("member={}",mapper.getMember(mbId));
    }
}