package com.lollipop.board.admin.role.controller;

import com.lollipop.board.common.model.ApiResponse;
import com.lollipop.board.admin.role.model.RoleDTO;
import com.lollipop.board.admin.role.model.RoleParam;
import com.lollipop.board.admin.role.service.RoleService;
import com.lollipop.board.common.model.PaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/roles")
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
    public ResponseEntity<ApiResponse<PaginationDTO<RoleDTO>>> retrieveRoleList(RoleParam roleParam) {
        PaginationDTO<RoleDTO> roleList = roleService.retrieveRoleList(roleParam);
        return new ResponseEntity<>(ApiResponse.success(roleList), HttpStatus.OK);
    }

    /**
     * 권한 조회
     *
     * @param roleId 권한 아이디
     * @return 권한
     */
    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleDTO>> retrieveRole(@PathVariable Integer roleId) {
        RoleDTO role = roleService.retrieveRoleById(roleId);
        return new ResponseEntity<>(ApiResponse.success(role), HttpStatus.OK);
    }

    /**
     * 권한 생성
     *
     * @param roleDTO 권한 정보
     * @return 저장된 권한 정보
     */
    @PostMapping
    public ResponseEntity<ApiResponse<RoleDTO>> createRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO savedRoleDTO = roleService.createRole(roleDTO);
        return new ResponseEntity<>(ApiResponse.success(savedRoleDTO), HttpStatus.OK);
    }

    /**
     * 권한 수정
     *
     * @param roleId  권한 아이디
     * @param roleDTO 권한 정보
     * @return 수정된 권한 정보
     */
    @PutMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleDTO>> modifyRole(@PathVariable Integer roleId, @RequestBody RoleDTO roleDTO) {
        roleDTO.setRoleId(roleId);
        RoleDTO savedRoleDTO = roleService.modifyRole(roleDTO);
        return new ResponseEntity<>(ApiResponse.success(savedRoleDTO), HttpStatus.OK);
    }

    /**
     * 권한 삭제
     *
     * @param roleId 권한 아이디
     * @return 삭제된 권한 정보
     */
    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Integer roleId) {
        roleService.deleteRole(roleId);
        return new ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
    }
}
