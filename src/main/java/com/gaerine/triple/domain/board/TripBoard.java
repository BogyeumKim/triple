package com.gaerine.triple.domain.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

    public Long getPeriod(){
        LocalDate start = this.start_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = this.end_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long days = ChronoUnit.DAYS.between(start, end);
        return days;
    }

    public LocalDate getViewStartDay(){
        LocalDate start = this.start_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return start;
    }

    public LocalDate getViewEndDay(){
        LocalDate end = this.end_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return end;
    }
}
