package com.gaerine.triple.domain.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class DayPlace {
    private Long day_id;
    private Long board_id;
    private String day1;
    private String day2;
    private String day3;
    private String day4;
    private String day5;
}
