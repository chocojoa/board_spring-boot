package com.lollipop.board.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lollipop.board.admin.user.model.LoginUserDTO;
import com.lollipop.board.setup.jwt.JwtToken;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AuthDTO {

    @JsonIgnore
    private JwtToken token;

    private LoginUserDTO user;
}
