package com.lollipop.board.admin.role.mapper;

import com.lollipop.board.admin.role.model.UserRoleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    /**
     * 권한별 사용자 목록 조회
     *
     * @param roleId 권한 아이디
     * @return 사용자 목록
     */
    List<UserRoleDTO> selectUserRoleList(Integer roleId);

    /**
     * 권한별 사용자 저장
     *
     * @param userRoleDTO 권한 및 사용자 목록
     */
    void insertUserRole(UserRoleDTO userRoleDTO);

    /**
     * 권한별 사용자 삭제
     *
     * @param userRoleDTO 권한 및 사용자 목록
     */
    void deleteUserRole(UserRoleDTO userRoleDTO);
}
