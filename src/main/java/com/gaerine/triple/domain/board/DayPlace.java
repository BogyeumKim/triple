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
    private Map<String, String> day1;
    private Map<String, String> day2;
    private Map<String, String> day3;
    private Map<String, String> day4;
    private Map<String, String> day5;
}
