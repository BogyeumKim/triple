package com.gaerine.triple.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Getter
@AllArgsConstructor
@ToString
public class World {

    private Long world_id;
    private String national_name;

}
