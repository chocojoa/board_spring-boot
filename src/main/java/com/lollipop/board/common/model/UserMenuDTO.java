package com.lollipop.board.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Alias("userMenuDTO")
public class UserMenuDTO {

    private Integer menuId;

    private String menuName;

    private String menuUrl;

    private Integer parentMenuId;

    private Integer groupId;

    private Integer depth;

    private Integer sortOrder;

    private Integer childCount;

    private String icon;

}
