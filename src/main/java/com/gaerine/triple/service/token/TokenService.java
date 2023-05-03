package com.gaerine.triple.service.token;

import com.gaerine.triple.domain.token.Token;

public interface TokenService {
    Token saveToken(Long memberId);
}
