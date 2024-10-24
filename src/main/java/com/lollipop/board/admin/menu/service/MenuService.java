package com.lollipop.board.admin.menu.service;

import com.lollipop.board.admin.menu.mapper.MenuMapper;
import com.lollipop.board.admin.menu.model.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuMapper menuMapper;

    public List<MenuDTO> retrieveMenus() {
        return menuMapper.selectMenus();
    }

    public MenuDTO createMenu(MenuDTO menuDTO) {
        menuMapper.createMenu(menuDTO);
        return menuDTO;
    }

    public MenuDTO modifyMenu(MenuDTO menuDTO) {
        menuMapper.updateMenu(menuDTO);
        return menuDTO;
    }

    public void removeMenu(Integer menuId) {
        menuMapper.deleteMenu(menuId);
    }
}
