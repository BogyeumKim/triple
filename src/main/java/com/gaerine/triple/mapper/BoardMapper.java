package com.gaerine.triple.mapper;

import com.gaerine.triple.domain.board.Capital;
import com.gaerine.triple.domain.board.DayPlace;
import com.gaerine.triple.domain.board.TripBoard;
import com.gaerine.triple.domain.board.World;
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

    @Select("select * from tripboard where user_id=#{id}")
    TripBoard selectBoardByUserId(String user_id);

    @Select("select * from dayplace where board_id=#{id}")
    DayPlace selectDayPlaceByBoardId(Long board_id);

    @Select("SELECT * from capital where capital_name LIKE  '%${input}%'")
    List<Capital> selectCapitalByLikeInput(String input);

    int insertTripBoard(TripBoard data);
}
