package com.lollipop.board.admin.role.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@ToString
@Alias("menuRoleDTO")
public class MenuRoleDTO {

    private Integer roleId;

    private Integer menuId;

    private String menuName;

    private Integer parentMenuId;

    private Integer depth;

    private Integer sortOrder;

    private String menuUrl;

    private Integer childCount;

    private Boolean halfChecked;

    private Boolean checked;

    private Integer createdBy;

    private List<Integer> addMenuList;
}
