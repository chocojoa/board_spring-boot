package com.lollipop.board.auth.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("loginParam")
public class AuthParam {

    private String email;

    private String password;

    private String refreshToken;
}
