package com.lollipop.board.admin.user.model;

import com.lollipop.board.common.model.PaginationParam;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Builder
@Alias("userParam")
public class UserParam extends PaginationParam {

    private Integer userId;

    private String userName;

    private String email;

    private String startCreatedDate;

    private String endCreatedDate;

}
