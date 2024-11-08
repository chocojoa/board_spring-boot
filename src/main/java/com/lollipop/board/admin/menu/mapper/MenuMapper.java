package com.lollipop.board.admin.menu.mapper;

import com.lollipop.board.admin.menu.model.MenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    /**
     * 메뉴 목록 조회
     *
     * @return 메뉴 목록
     */
    List<MenuDTO> selectMenus();

    /**
     * 메뉴 상세조회
     *
     * @param id 메뉴 아이디
     * @return 메뉴
     */
    MenuDTO selectMenuById(Integer id);

    /**
     * 메뉴 저장
     *
     * @param menuDTO 메뉴 정보
     */
    void insertMenu(MenuDTO menuDTO);

    /**
     * 메뉴 수정
     *
     * @param menuDTO 메뉴 정보
     */
    void updateMenu(MenuDTO menuDTO);

    /**
     * 메뉴 삭제
     *
     * @param menuId 메뉴 아이디
     */
    void deleteMenu(Integer menuId);

    /**
     * 네비게이션 메뉴 조회
     *
     * @param menuName 메뉴명
     * @return 메뉴 목록
     */
    List<MenuDTO> selectBreadcrumbs(String menuName);

}
