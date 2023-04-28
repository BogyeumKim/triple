package com.gaerine.triple.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {

    @Select("SELECT NOW()")
    String  getTime();

    String getTime2();
}
