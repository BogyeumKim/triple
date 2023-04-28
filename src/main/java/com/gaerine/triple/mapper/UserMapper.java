package com.gaerine.triple.mapper;

import com.gaerine.triple.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    void save(Member mb);
}
