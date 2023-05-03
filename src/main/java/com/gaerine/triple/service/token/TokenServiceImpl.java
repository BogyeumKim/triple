package com.gaerine.triple.service.token;

import com.gaerine.triple.domain.token.Token;
import com.gaerine.triple.mapper.TokenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final TokenMapper mapper;

    @Override
    public Token saveToken(Long memberId) {
        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();

        Token token = new Token();
        token.setAccess_token(accessToken);
        token.setRefresh_token(refreshToken);
        token.setMember_id(memberId);

        int result = mapper.insertToken(token);
        log.info("token={}",token);
        if(result == 0) {
            return null;
        }
        return token;
    }
}
