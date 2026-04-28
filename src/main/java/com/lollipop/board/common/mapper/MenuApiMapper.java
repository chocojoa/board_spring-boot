package com.lollipop.board.common.mapper;

import com.lollipop.board.common.model.MenuApiDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuApiMapper {

    List<MenuApiDTO> selectMenuApiList(String email);

}
