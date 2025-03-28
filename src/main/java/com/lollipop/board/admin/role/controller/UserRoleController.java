package com.lollipop.board.admin.role.controller;

import com.lollipop.board.admin.role.model.UserRoleDTO;
import com.lollipop.board.admin.role.service.UserRoleService;
import com.lollipop.board.setup.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/userRole")
public class UserRoleController {

    private final UserRoleService userRoleService;

    /**
     * 권한별 사용자 조회
     *
     * @param roleId 권한 아이디
     * @return 메뉴 목록
     */
    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse<List<UserRoleDTO>>> retrieveUserRoleList(@PathVariable("roleId") Integer roleId) {
        List<UserRoleDTO> userRoleList = userRoleService.retrieveUserRoleList(roleId);
        return new ResponseEntity<>(ApiResponse.success(userRoleList), HttpStatus.OK);
    }

    /**
     * 권한별 사용자 저장
     *
     * @param roleId      권한 아이디
     * @param userRoleDTO 메뉴 정보
     * @return null
     */
    @PostMapping("/{roleId}")
    public ResponseEntity<ApiResponse<Void>> createUserRole(@PathVariable("roleId") Integer roleId, @RequestBody UserRoleDTO userRoleDTO) {
        userRoleDTO.setRoleId(roleId);
        userRoleService.createUserRole(userRoleDTO);
        return new ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
    }
}
