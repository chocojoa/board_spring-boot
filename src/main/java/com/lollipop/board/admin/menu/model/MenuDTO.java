package com.lollipop.board.admin.menu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("menuDTO")
public class MenuDTO {

    private Integer menuId;

    private String menuName;

    private String menuUrl;

    private Integer parentMenuId;

    private Integer groupId;

    private Integer depth;

    private Integer sortOrder;

    private Integer childCount;

    private Boolean usageStatus;

    private Boolean isDisplayed;

    private String icon;

    private Integer userId;
}
