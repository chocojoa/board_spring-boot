package com.lollipop.board.admin.user.controller;

import com.lollipop.board.common.model.ApiResponse;
import com.lollipop.board.common.model.PaginationDTO;
import com.lollipop.board.admin.user.model.UserDTO;
import com.lollipop.board.admin.user.model.UserParam;
import com.lollipop.board.admin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class UserController {

    private final UserService userService;

    /**
     * 사용자 목록 조회
     *
     * @param userParam 검색조건
     * @return 사용자 목록
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PaginationDTO<UserDTO>>> retrieveUserList(
            UserParam userParam) {
        PaginationDTO<UserDTO> userList = userService.retrieveUserList(userParam);
        return ResponseEntity.ok().body(ApiResponse.success(userList));
    }

    /**
     * 사용자 정보 조회
     *
     * @param userId 사용자 아이디
     * @return 사용자 정보
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> retrieveUser(@PathVariable int userId) {
        UserDTO userDTO = userService.retrieveUser(userId);
        return ResponseEntity.ok().body(ApiResponse.success(userDTO));
    }

    /**
     * 사용자 정보 생성
     *
     * @param userDTO 사용자 정보
     * @return 저장된 사용자 정보
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUserDTO = userService.createUser(userDTO);
        return ResponseEntity.ok().body(ApiResponse.success(createdUserDTO));
    }

    /**
     * 사용자 정보 수정
     *
     * @param userDTO 사용자 정보
     */
    @PutMapping
    public ResponseEntity<ApiResponse<UserDTO>> modifyUser(@RequestBody UserDTO userDTO) {
        UserDTO modifiedUserDTO = userService.modifyUser(userDTO);
        return ResponseEntity.ok().body(ApiResponse.success(modifiedUserDTO));
    }
}
