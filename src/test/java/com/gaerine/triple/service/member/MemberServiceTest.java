package com.gaerine.triple.service.member;

import com.gaerine.triple.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
class MemberServiceTest {

    @Autowired MemberService service;

    @Test
    @Transactional
    void register() {
        // given
        Member mb = new Member();
        mb.setUser_nick("테스트닉넴");
        mb.setUser_id("테스터");
        mb.setUser_pw("비번");

        // when
        Member register = service.register(mb);
        log.info("mb={}",mb);

        // then
        Member byId = service.findById(mb.getMember_id());
        Assertions.assertThat(register).isEqualTo(byId);
    }
}