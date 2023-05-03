package com.gaerine.triple.domain.token;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Token {
    private Long id;
    private Long member_id;
    private String access_token;
    private String refresh_token;
    private Date created_date;
}
