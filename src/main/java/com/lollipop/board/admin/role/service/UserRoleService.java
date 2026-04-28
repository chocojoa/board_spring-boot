package com.lollipop.board.admin.role.service;

import com.lollipop.board.admin.role.mapper.UserRoleMapper;
import com.lollipop.board.admin.role.model.UserRoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleMapper userRoleMapper;

    /**
     * 권한별 사용자 목록 조회
     *
     * @param roleId 권한 아이디
     * @return 권한있는 사용자 목록
     */
    public List<UserRoleDTO> retrieveUserRoleList(Integer roleId) {
        return userRoleMapper.selectUserRoleList(roleId);
    }

    /**
     * 권한별 사용자 정보 저장
     *
     * @param userRoleDTO 권한 및 사용자 목록
     */
    public void createUserRole(UserRoleDTO userRoleDTO) {
        userRoleMapper.deleteUserRole(userRoleDTO);
        if (!userRoleDTO.getAddUserList().isEmpty()) {
            userRoleMapper.insertUserRole(userRoleDTO);
        }
    }

}
