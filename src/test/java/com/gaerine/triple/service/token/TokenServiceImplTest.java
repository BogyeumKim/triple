package com.gaerine.triple.service.token;

import com.gaerine.triple.domain.token.Expires;
import com.gaerine.triple.domain.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

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

    @Test
    @Transactional
    void getToken() {
        // given
        Token saveToken = service.saveToken(new Long(1));
        // when
        Token token = service.getToken(saveToken.getAccess_token());
        // then
        Assertions.assertThat(saveToken).usingRecursiveComparison().ignoringFieldsOfTypes(Date.class, Date.class).isEqualTo(token);
    }

    @Test
    @Transactional
    void updateToken(){
        // given
        Token saveToken = service.saveToken(new Long(1));

        /* now() -4000*/
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -4000);
        Date resultDate = new Date(calendar.getTimeInMillis());

        service.modifyCreatedDate(resultDate, saveToken.getAccess_token());

        // when
        Token token = service.getToken(saveToken.getAccess_token());

        // then
        Assertions.assertThat(saveToken.getAccess_token()).isNotEqualTo(token.getAccess_token());
        Assertions.assertThat(token.getUpdate_date()).isNotNull();

    }
}