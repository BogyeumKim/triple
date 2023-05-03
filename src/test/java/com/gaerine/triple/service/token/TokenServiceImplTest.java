package com.gaerine.triple.service.token;

import com.gaerine.triple.domain.token.Token;
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
class TokenServiceImplTest {

    @Autowired TokenService service;

    @Test
    @Transactional
    void saveToken() {
        Token result = service.saveToken(new Long(1));
        log.info("saveToken={}",result);
        Assertions.assertThat(result).isNotNull();
    }
}