package com.gaerine.triple.domain.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TripBoard {
    private Long board_id;
    private String user_id;
    private Long location;
    private Long capital;
    private Date start_date;
    private Date end_date;

}
