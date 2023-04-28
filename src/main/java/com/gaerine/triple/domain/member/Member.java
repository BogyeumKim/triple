package com.gaerine.triple.domain.member;

import lombok.Data;

@Data
public class Member {

    private Long member_id;
    private String user_id;
    private String user_pw;
    private String user_nick;
    private String social_id;
    private String img_url;

}
