package com.lollipop.board.admin.role.controller;

import com.lollipop.board.admin.role.model.MenuRoleDTO;
import com.lollipop.board.admin.role.service.MenuRoleService;
import com.lollipop.board.setup.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/menuRole")
public class MenuRoleController {

    private final MenuRoleService menuRoleService;

    /**
     * 권한별 메뉴조회
     *
     * @param roleId 권한 아이디
     * @return 메뉴 목록
     */
    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse<List<MenuRoleDTO>>> retrieveMenuRole(@PathVariable("roleId") Integer roleId) {
        List<MenuRoleDTO> menuRoleDTOList = menuRoleService.retrieveMenuRoleList(roleId);
        return new ResponseEntity<>(ApiResponse.success(menuRoleDTOList), HttpStatus.OK);
    }

    /**
     * 권한별 메뉴저장
     *
     * @param roleId      권한 아이디
     * @param menuRoleDTO 메뉴 저장
     * @return null
     */
    @PostMapping("/{roleId}")
    public ResponseEntity<ApiResponse<Void>> createMenuRole(@PathVariable("roleId") Integer roleId, @RequestBody MenuRoleDTO menuRoleDTO) {
        menuRoleDTO.setRoleId(roleId);
        menuRoleService.createMenuRole(menuRoleDTO);
        return new ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
    }
}
