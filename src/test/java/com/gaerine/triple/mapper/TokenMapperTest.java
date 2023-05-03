package com.gaerine.triple.mapper;

import com.gaerine.triple.domain.token.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class TokenMapperTest {

    @Autowired TokenMapper mapper;

    @Test
    void insertToken() {
        Token token = new Token();
        token.setAccess_token("ACCCCCCCCC");
        token.setRefresh_token("REFFFFFFFFFF");
        token.setMember_id(new Long(1));

        int result = mapper.insertToken(token);

        log.info("token = {}",token);
        Assertions.assertThat(result).isEqualTo(1);
        Assertions.assertThat(token.getId()).isNotNull();
    }
}