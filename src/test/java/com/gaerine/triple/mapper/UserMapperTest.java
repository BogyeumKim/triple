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
    void getMemberId() {
        log.info("member={}",mapper.getMemberId("테스터"));
    }

    @Test
    void getMemberLogin() {
        Member mb = new Member();
        mb.setUser_id("test1");
        mb.setUser_pw("test1");

        log.info("member={}",mapper.getMemberLogin(mb.getUser_id(),mb.getUser_pw()));
    }
}