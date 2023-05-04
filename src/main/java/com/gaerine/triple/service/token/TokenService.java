package com.gaerine.triple.service.token;

import com.gaerine.triple.domain.token.Token;

import java.util.Date;

public interface TokenService {
    Token saveToken(Long memberId);

    Token getToken(String token);

    int modifyToken(String newToken,String oldToken);

    int modifyCreatedDate(Date date,String token);

    Token getTokenById(Long id);
}
