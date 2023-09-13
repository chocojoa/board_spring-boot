package com.lollipop.board.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("userDTO")
public class UserDTO {

    private int userId;

    private String userName;

    private String email;

    @JsonIgnore
    private String password;

    private String roleId;

    @JsonIgnore
    private int createdBy;

}
