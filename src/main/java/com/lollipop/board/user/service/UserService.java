package com.lollipop.board.user.service;

import com.lollipop.board.common.model.PaginationDTO;
import com.lollipop.board.user.mapper.UserMapper;
import com.lollipop.board.user.model.UserDTO;
import com.lollipop.board.user.model.UserParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    /**
     * 사용자 목록 조회
     * @param userParam 검색조건
     * @return 사용자 목록
     */
    public PaginationDTO<UserDTO> retrieveUserList(UserParam userParam) {
        int totalCount = userMapper.selectUserCount(userParam);
        List<UserDTO> userList = userMapper.selectUserList(userParam);
        return new PaginationDTO<>(totalCount, userList);
    }

    /**
     * 사용자 상세 조회
     * @param userId 사용자 아이디
     * @return 사용자 정보
     */
    public UserDTO retrieveUser(int userId) {
        UserParam userParam = UserParam.builder().userId(userId).build();
        return userMapper.selectUser(userParam);
    }

    /**
     * 사용자 생성
     * @param userDTO 사용자 정보
     * @return 저장된 사용자 정보
     */
    public UserDTO createUser(UserDTO userDTO) {
        userMapper.insertUser(userDTO);
        return userDTO;
    }

    /**
     * 사용자 수정
     * @param userDTO 사용자 정보
     * @return 수정된 사용자 정보
     */
    public UserDTO modifyUser(UserDTO userDTO) {
        userMapper.updateUser(userDTO);
        return userDTO;
    }

}