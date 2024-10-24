package com.lollipop.board.role.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("roleDTO")
public class RoleDTO {

    private Integer id;

    private String roleName;

    private String description;

}
