package com.lollipop.board.common.mapper;

import com.lollipop.board.admin.user.model.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {

    void updateProfile(UserDTO userDTO);
}
