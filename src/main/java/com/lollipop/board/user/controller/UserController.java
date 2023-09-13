package com.lollipop.board.user.controller;

import com.lollipop.board.common.model.WrapperDTO;
import com.lollipop.board.user.model.UserDTO;
import com.lollipop.board.user.model.UserParam;
import com.lollipop.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * 사용자 정보 목록 조회
     * @param userParam
     * @return
     */
    @PostMapping("/users")
    @ResponseBody
    public WrapperDTO retrieveUserList(UserParam userParam){
        return userService.retrieveUserList(userParam);
    }

    /**
     * 사용자 정보 조회
     * @param userParam
     * @return
     */
    @GetMapping("/user")
    @ResponseBody
    public UserDTO retrieveUser(UserParam userParam){
        return userService.retrieveUser(userParam);
    }

    /**
     * 사용자 정보 생성
     * @param userDTO
     */
    @PostMapping("/user")
    @ResponseBody
    public void createUser(@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
    }

    /**
     * 사용자 정보 수정
     * @param userDTO
     */
    @PutMapping("/user")
    @ResponseBody
    public void modifyUser(@RequestBody UserDTO userDTO){
        userService.modifyUser(userDTO);
    }

}
