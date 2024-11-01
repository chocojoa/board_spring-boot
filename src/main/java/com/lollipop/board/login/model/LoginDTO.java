package com.lollipop.board.login.model;

import com.lollipop.board.admin.user.model.LoginUserDTO;
import com.lollipop.board.jwt.JwtToken;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class LoginDTO {

    private JwtToken token;

    private LoginUserDTO user;
}
