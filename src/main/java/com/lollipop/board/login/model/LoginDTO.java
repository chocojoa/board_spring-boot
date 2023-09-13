package com.lollipop.board.login.model;

import com.lollipop.board.jwt.JwtToken;
import com.lollipop.board.user.model.UserDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {

    private JwtToken token;

    private UserDTO user;
}
