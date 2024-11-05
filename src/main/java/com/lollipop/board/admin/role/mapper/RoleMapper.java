package com.lollipop.board.admin.role.mapper;

import com.lollipop.board.admin.role.model.RoleDTO;
import com.lollipop.board.admin.role.model.RoleParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    /**
     * 권한목록 조회
     *
     * @param roleParam 검색조건
     * @return 권한목록
     */
    List<RoleDTO> selectRoleList(RoleParam roleParam);

    /**
     * 권한생성
     *
     * @param roleDTO 권한정보
     */
    void insertRole(RoleDTO roleDTO);

    /**
     * 권한수정
     *
     * @param roleDTO 권한정보
     */
    void updateRole(RoleDTO roleDTO);

    /**
     * 권한삭제
     *
     * @param roleDTO 권한정보
     */
    void deleteRole(RoleDTO roleDTO);
}
