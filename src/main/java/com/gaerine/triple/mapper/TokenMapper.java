package com.gaerine.triple.mapper;

import com.gaerine.triple.domain.token.Token;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapper {

    int insertToken(Token token);
}
