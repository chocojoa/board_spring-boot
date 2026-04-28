package com.lollipop.board.admin.role.mapper;

import com.lollipop.board.admin.role.model.RoleDTO;
import com.lollipop.board.admin.role.model.RoleParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    /**
     * 권한개수 조회
     *
     * @param roleParam 검색조건
     * @return 권한개수
     */
    Integer selectRoleCount(RoleParam roleParam);

    /**
     * 권한목록 조회
     *
     * @param roleParam 검색조건
     * @return 권한목록
     */
    List<RoleDTO> selectRoleList(RoleParam roleParam);

    /**
     * 권한정보 조회
     *
     * @param roleId 권한 아이디
     * @return 권한정보
     */
    RoleDTO selectRoleById(Integer roleId);

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
     * @param roleId 권한아이디
     */
    void deleteRoleById(Integer roleId);
}
