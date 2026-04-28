package com.lollipop.board.admin.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("loginUserDTO")
public class LoginUserDTO {

    private Integer userId;

    private String userName;

    private String email;

    @JsonIgnore
    private String password;
}
