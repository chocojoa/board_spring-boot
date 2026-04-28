package com.lollipop.board.admin.role.mapper;

import com.lollipop.board.admin.role.model.MenuRoleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuRoleMapper {

    /**
     * 권한별 메뉴 조회
     *
     * @param roleId 권한 아이디
     * @return 메뉴 목록
     */
    List<MenuRoleDTO> selectMenuRoleList(Integer roleId);

    /**
     * 권한별 메뉴 저장
     *
     * @param menuRoleDTO 메뉴 정보
     */
    void insertMenuRole(MenuRoleDTO menuRoleDTO);

    /**
     * 권한별 메뉴 삭제
     *
     * @param menuRoleDTO 메뉴 정보
     */
    void deleteMenuRole(MenuRoleDTO menuRoleDTO);
}
