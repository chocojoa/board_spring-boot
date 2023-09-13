package com.lollipop.board.user.service;

import com.lollipop.board.common.model.WrapperDTO;
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
     * 고객 목록 조회
     * @param userParam
     * @return
     */
    public WrapperDTO retrieveUserList(UserParam userParam) {
        int totalCount = userMapper.selectUserCount(userParam);
        List<UserDTO> userList = userMapper.selectUserList(userParam);
        return new WrapperDTO(userParam.getDraw(), totalCount, userList);
    }

    /**
     * 고객 정보 조회
     * @param userParam
     * @return
     */
    public UserDTO retrieveUser(UserParam userParam) {
        return userMapper.selectUser(userParam);
    }

    /**
     * 고객 정보 저장
     * @param userDTO
     * @return
     */
    public int createUser(UserDTO userDTO) {
        return userMapper.insertUser(userDTO);
    }

    /**
     * 고객 정보 수정
     * @param userDTO
     * @return
     */
    public int modifyUser(UserDTO userDTO) {
        return userMapper.updateUser(userDTO);
    }

}
