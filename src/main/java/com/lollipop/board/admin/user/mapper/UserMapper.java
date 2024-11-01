package com.lollipop.board.admin.user.mapper;

import com.lollipop.board.admin.user.model.LoginUserDTO;
import com.lollipop.board.admin.user.model.UserDTO;
import com.lollipop.board.admin.user.model.UserParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 사용자 전체 개수 조회
     *
     * @param userParam 검색조건
     * @return 사용자 수
     */
    int selectUserCount(UserParam userParam);

    /**
     * 사용자 목록 조회
     *
     * @param userParam 검색조건
     * @return 사용자 목록
     */
    List<UserDTO> selectUserList(UserParam userParam);

    /**
     * 사용자 정보 조회
     *
     * @param userParam 검색조건
     * @return 사용자 정보
     */
    UserDTO selectUser(UserParam userParam);

    /**
     * 이메일주소로 사용자 정보 조회
     *
     * @param email 이메일 주소
     * @return 사용자 정보
     */
    LoginUserDTO selectUserByEmail(String email);

    /**
     * 사용자 정보 저장
     *
     * @param userDTO 사용자 정보
     */
    void insertUser(UserDTO userDTO);

    /**
     * 사용자 정보 수정
     *
     * @param userDTO 사용자 정보
     */
    void updateUser(UserDTO userDTO);

}
