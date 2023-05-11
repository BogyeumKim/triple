package com.gaerine.triple.domain.board;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Place {
    private Long id;
    private Long capital_id;
    private String korea_name;
    private String real_name;
    private String category;
    private Double lat;
    private Double lng;
}
