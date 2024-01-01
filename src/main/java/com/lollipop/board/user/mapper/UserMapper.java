package com.lollipop.board.user.mapper;

import com.lollipop.board.user.model.UserDTO;
import com.lollipop.board.user.model.UserParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 사용자 목록 개수 조회
     * @param userParam
     * @return
     */
    int selectUserCount(UserParam userParam);

    /**
     * 사용자 목록 조회
     * @param userParam
     * @return
     */
    List<UserDTO> selectUserList(UserParam userParam);

    /**
     * 사용자 정보 조회
     * @param userParam
     * @return
     */
    UserDTO selectUser(UserParam userParam);

    /**
     * 이메일주소로 사용자 정보 조회
     * @param userParam
     * @return
     */
    UserDTO selectUserByEmail(UserParam userParam);

    /**
     * 사용자 정보 저장
     * @param userDTO
     * @return
     */
    int insertUser(UserDTO userDTO);

    /**
     * 사용자 정보 수정
     * @param userDTO
     * @return
     */
    int updateUser(UserDTO userDTO);

}
