package com.gaerine.triple.mapper;

import com.gaerine.triple.domain.board.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Select("select * from world")
    List<World> selectWorld();

    @Select("select * from capital")
    List<Capital> selectCapital();

    @Select("select * from tripboard where board_id=#{id}")
    TripBoard selectBoardByBoardId(Long board_id);

    @Select("select * from dayplace where board_id=#{id}")
    DayPlace selectDayPlaceByBoardId(Long board_id);

    @Select("SELECT * from capital where capital_name LIKE  '%${input}%'")
    List<Capital> selectCapitalByLikeInput(String input);

    int insertTripBoard(TripBoard data);

    @Select("select * from capital where capital_id=#{id}")
    Capital selectCapitalById(Long capital_id);

    @Select("select * from place where capital_id=#{id}")
    List<Place> selectPlaceByCapital(Long id);
}
