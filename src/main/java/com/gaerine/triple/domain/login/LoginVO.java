package com.gaerine.triple.domain.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginVO {
    private String user_id;
    private String user_pw;
}
