package com.gaerine.triple.mapper;

import com.gaerine.triple.domain.token.Token;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

@Mapper
public interface TokenMapper {

    int insertToken(Token token);

    @Select("select * from token where access_token=#{token}")
    Token selectToken(String token);

    @Update("update token set access_token =#{newToken}, update_date=now() where access_token=#{oldToken}")
    int updateToken(@Param("newToken") String newToken,@Param("oldToken") String oldToken);

    @Update("update token set created_date=#{newDate} where access_token=#{token}")
    int updateCreatedDate(@Param("newDate") Date date,@Param("token") String token);
}
