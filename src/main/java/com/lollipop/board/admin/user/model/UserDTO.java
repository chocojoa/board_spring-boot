package com.lollipop.board.admin.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("userDTO")
public class UserDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int rowNumber;

    private int userId;

    private String userName;

    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isPasswordChange;

    private String password = "";

    private String verifyPassword = "";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String roleId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int createdBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int modifiedBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String modifiedAt;

}
