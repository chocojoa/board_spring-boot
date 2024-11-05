package com.lollipop.board.admin.role.service;

import com.lollipop.board.admin.role.mapper.RoleMapper;
import com.lollipop.board.admin.role.model.RoleDTO;
import com.lollipop.board.admin.role.model.RoleParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<RoleDTO> retrieveRoleList(RoleParam roleParam) {
        return roleMapper.selectRoleList(roleParam);
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
        roleMapper.updateRole(roleDTO);
        return roleDTO;
    }

    /**
     * 권한삭제
     *
     * @param roleDTO 권한정보
     */
    public void deleteRole(RoleDTO roleDTO) {
        roleMapper.deleteRole(roleDTO);
    }
}
