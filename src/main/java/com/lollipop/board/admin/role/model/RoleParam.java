package com.lollipop.board.admin.role.model;

import com.lollipop.board.common.model.PaginationParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("roleParam")
public class RoleParam extends PaginationParam {

    private Integer id;

    private String roleName;

    private String description;

    private Integer userId;

}
