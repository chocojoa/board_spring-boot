package com.lollipop.board.admin.role.service;

import com.lollipop.board.admin.role.mapper.MenuRoleMapper;
import com.lollipop.board.admin.role.model.MenuRoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuRoleService {

    private final MenuRoleMapper menuRoleMapper;

    /**
     * 권한별 메뉴 목록 조회
     *
     * @param roleId 권한 아이디
     * @return 메뉴 목록
     */
    public List<MenuRoleDTO> retrieveMenuRoleList(Integer roleId) {
        return menuRoleMapper.selectMenuRoleList(roleId);
    }

    /**
     * 권한별 메뉴 저장
     *
     * @param menuRoleDTO 권한 & 메뉴 정보
     */
    public void createMenuRole(MenuRoleDTO menuRoleDTO) {
        menuRoleMapper.deleteMenuRole(menuRoleDTO);
        if (!menuRoleDTO.getAddMenuList().isEmpty()) {
            menuRoleMapper.insertMenuRole(menuRoleDTO);
        }
    }
}
