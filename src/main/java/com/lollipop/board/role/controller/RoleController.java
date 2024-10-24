package com.lollipop.board.role.controller;

import com.lollipop.board.common.model.ApiResponse;
import com.lollipop.board.role.model.RoleDTO;
import com.lollipop.board.role.model.RoleParam;
import com.lollipop.board.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 권한 목록 조회
     *
     * @param roleParam 검색조건
     * @return 권한 목록
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleDTO>>> retrieveRoleList(RoleParam roleParam) {
        List<RoleDTO> roleList = roleService.retrieveRoleList(roleParam);
        return ResponseEntity.ok(ApiResponse.success(roleList));
    }

    /**
     * 권한 생성
     *
     * @param roleDTO 권한 정보
     * @return 저장된 권한 정보
     */
    @PostMapping
    public ResponseEntity<ApiResponse<RoleDTO>> createRole(RoleDTO roleDTO) {
        RoleDTO savedRoleDTO = roleService.createRole(roleDTO);
        return ResponseEntity.ok().body(ApiResponse.success(savedRoleDTO));
    }

    /**
     * 권한 수정
     *
     * @param roleId  권한 아이디
     * @param roleDTO 권한 정보
     * @return 수정된 권한 정보
     */
    @PutMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleDTO>> modifyRole(@PathVariable Integer roleId, RoleDTO roleDTO) {
        roleDTO.setId(roleId);
        RoleDTO savedRoleDTO = roleService.modifyRole(roleDTO);
        return ResponseEntity.ok().body(ApiResponse.success(savedRoleDTO));
    }

    /**
     * 권한 삭제
     *
     * @param roleId  권한 아이디
     * @param roleDTO 권한 정보
     * @return 삭제된 권한 정보
     */
    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Integer roleId, RoleDTO roleDTO) {
        roleDTO.setId(roleId);
        roleService.deleteRole(roleDTO);
        return ResponseEntity.ok().body(ApiResponse.success(null));
    }
}
