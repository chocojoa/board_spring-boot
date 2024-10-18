package com.lollipop.board.admin.menu.mapper;

import com.lollipop.board.admin.menu.model.MenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<MenuDTO> selectMenus();

    void createMenu(MenuDTO menuDTO);

    void updateMenu(MenuDTO menuDTO);

    void deleteMenu(Integer menuId);

}
