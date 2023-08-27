package com.gaerine.triple.service.token;

import com.gaerine.triple.domain.token.Expires;
import com.gaerine.triple.domain.token.Token;
import com.gaerine.triple.exception.TokenExpiredException;
import com.gaerine.triple.exception.TokenNotFoundException;
import com.gaerine.triple.mapper.TokenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final TokenMapper mapper;

    @Override
    public Token getTokenById(Long id) {
        return mapper.selectTokenById(id);
    }

    @Override
    public int modifyCreatedDate(Date date, String token) {
        return mapper.updateCreatedDate(date,token);
    }

    @Override
    public int modifyToken(String newToken, String refreshToken) {
        return mapper.updateToken(newToken, refreshToken);
    }

    @Override
    public Token getLoginToken(String token) {
        Token getToken = mapper.selectToken(token);
        if(getToken == null){
            return null;
        }

        Date getTokenDate = null;
        if(getToken.getUpdate_date() == null ){
            getTokenDate = getToken.getCreated_date();
        } else {
            getTokenDate = getToken.getUpdate_date();
        }

        // Date to LocalDateTime and plus expires
        LocalDateTime expriesDate = LocalDateTime.ofInstant(getTokenDate.toInstant(), ZoneId.systemDefault()).plusSeconds(Expires.EXPIRES_IN);
        log.info("expriesDateLogin={}",expriesDate);
        boolean result = LocalDateTime.now().isAfter(expriesDate);

        // if Token expires
        if(result == true) {
            log.info("expires Token....");
            String newToken = UUID.randomUUID().toString();

            // refresh token select
            String refreshToken = getToken.getRefresh_token();
            int modifyResult = modifyToken(newToken, refreshToken);/* update access_Token , refresh token*/
                if(modifyResult == 1 ){
                    getToken.setAccess_token(newToken);
                }
        }

        return getToken;
    }

    @Override
    public Token validationToken(String token) {
        Token getToken = mapper.selectToken(token);
        if(getToken == null){
            throw new TokenNotFoundException();
        }

        Date getTokenDate = null;
        if(getToken.getUpdate_date() == null ){
            getTokenDate = getToken.getCreated_date();
        } else {
            getTokenDate = getToken.getUpdate_date();
        }

        // Date to LocalDateTime and plus expires
        LocalDateTime expriesDate = LocalDateTime.ofInstant(getTokenDate.toInstant(), ZoneId.systemDefault()).plusSeconds(Expires.EXPIRES_IN);
        log.info("expriesDateValid={}",expriesDate);
        boolean result = LocalDateTime.now().isAfter(expriesDate);

        // if Token expires
        if(result == true) {
            throw new TokenExpiredException();
        }

        return getToken;
    }

    @Override
    public Token saveToken(Long memberId) {
        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();

        Token token = new Token();
        token.setAccess_token(accessToken);
        token.setRefresh_token(refreshToken);
        token.setMember_id(memberId);
        token.setCreated_date(new Date());

        int result = mapper.insertToken(token);
        log.info("token={}",token);
        if(result == 0) {
            return null;
        }
        return token;
    }
}
