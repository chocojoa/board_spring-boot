package com.lollipop.board.admin.role.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@ToString
@Alias("userRoleDTO")
public class UserRoleDTO {

    private Integer userId;

    private Integer roleId;

    private Integer createdBy;

    private String userName;

    private String email;

    private List<Integer> addUserList;
    
}
