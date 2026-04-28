package com.lollipop.board.admin.role.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("roleDTO")
public class RoleDTO {

    private Integer rowNumber;

    private Integer roleId;

    private String roleName;

    private String description;

    private String createdAt;

    private String createdBy;

    private Integer userId;

}
