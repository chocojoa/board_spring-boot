package com.lollipop.board.login.model;

import com.lollipop.board.jwt.JwtToken;
import com.lollipop.board.user.model.UserDTO;
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

    private UserDTO user;
}
