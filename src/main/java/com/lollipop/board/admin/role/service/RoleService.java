package com.lollipop.board.admin.role.service;

import com.lollipop.board.admin.role.mapper.RoleMapper;
import com.lollipop.board.admin.role.model.RoleDTO;
import com.lollipop.board.admin.role.model.RoleParam;
import com.lollipop.board.common.model.PaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;

    /**
     * 권한목록 조회
     *
     * @param roleParam 검색조건
     * @return 권한목록
     */
    public PaginationDTO<RoleDTO> retrieveRoleList(RoleParam roleParam) {
        Integer totalCount = roleMapper.selectRoleCount(roleParam);
        List<RoleDTO> dataList = roleMapper.selectRoleList(roleParam);
        return new PaginationDTO<>(totalCount, dataList);
    }

    /**
     * 권한 조회
     *
     * @param roleId 권한 아이디
     * @return 권한
     */
    public RoleDTO retrieveRoleById(Integer roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 권한저장
     *
     * @param roleDTO 권한정보
     * @return 저장된 권한정보
     */
    public RoleDTO createRole(RoleDTO roleDTO) {
        roleMapper.insertRole(roleDTO);
        return roleDTO;
    }

    /**
     * 권한수정
     *
     * @param roleDTO 권한정보
     * @return 수정된 권한정보
     */
    public RoleDTO modifyRole(RoleDTO roleDTO) {
        RoleDTO role = roleMapper.selectRoleById(roleDTO.getRoleId());
        if (role == null) {
            throw new NoSuchElementException("role not found");
        }
        roleMapper.updateRole(roleDTO);
        return roleDTO;
    }

    /**
     * 권한삭제
     *
     * @param roleId 권한정보
     */
    public void deleteRole(Integer roleId) {
        RoleDTO role = roleMapper.selectRoleById(roleId);
        if (role == null) {
            throw new NoSuchElementException("role not found");
        }
        roleMapper.deleteRoleById(roleId);
    }
}
