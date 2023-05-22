package com.gaerine.triple.domain.board;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class Place {
    private Long id;
    @NotNull
    private Long capital_id;
    @NotNull
    private String korea_name;
    @NotNull
    private String category;
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;
}
