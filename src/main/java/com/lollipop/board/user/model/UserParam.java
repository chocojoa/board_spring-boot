package com.lollipop.board.user.model;

import com.lollipop.board.common.model.DataTableDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@Data
@Alias("userParam")
@EqualsAndHashCode(callSuper = false)
public class UserParam extends DataTableDTO {

    private int userId;

    private String userName;

    private String email;

    private String startCreatedDate;

    private String endCreatedDate;

}
