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

    /**
     * 메뉴 목록 조회
     *
     * @return 메뉴 목록
     */
    public List<MenuDTO> retrieveMenus() {
        return menuMapper.selectMenus();
    }

    /**
     * 메뉴 상세조회
     *
     * @param id 메뉴 아이디
     * @return 메뉴
     */
    public MenuDTO retrieveMenuById(Integer id) {
        return menuMapper.selectMenuById(id);
    }

    /**
     * 메뉴 저장
     *
     * @param menuDTO 메뉴 정보
     * @return 저장된 메뉴
     */
    public MenuDTO createMenu(MenuDTO menuDTO) {
        menuMapper.insertMenu(menuDTO);
        return menuDTO;
    }

    /**
     * 메뉴 수정
     *
     * @param menuDTO 메뉴 정보
     * @return 수정된 메뉴
     */
    public MenuDTO modifyMenu(MenuDTO menuDTO) {
        menuMapper.updateMenu(menuDTO);
        return menuDTO;
    }

    /**
     * 메뉴 삭제
     *
     * @param menuId 메뉴 아이디
     */
    public void removeMenu(Integer menuId) {
        menuMapper.deleteMenu(menuId);
    }
}
