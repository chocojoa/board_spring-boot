package com.lollipop.board.user.model;

import com.lollipop.board.common.model.DataTableDTO;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Builder
@Alias("userParam")
public class UserParam extends DataTableDTO {

    private int userId;

    private String userName;

    private String email;

    private String startCreatedDate;

    private String endCreatedDate;

}
