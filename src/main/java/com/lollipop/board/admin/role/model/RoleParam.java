package com.lollipop.board.admin.role.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("roleParam")
public class RoleParam {

    private Integer id;

    private String roleName;

    private String description;

    private Integer userId;

}
