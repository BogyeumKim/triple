package com.gaerine.triple.domain.board;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Capital {
    private Long capital_id;
    private Long world_id;
    private String capital_name;
    private Double lat;
    private Double lng;

}
