package com.lollipop.board.login.controller;

import com.lollipop.board.common.model.ApiResponse;
import com.lollipop.board.login.model.LoginDTO;
import com.lollipop.board.login.model.LoginParam;
import com.lollipop.board.login.service.LoginService;
import com.lollipop.board.user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    /**
     * 로그인
     * @param loginParam login parameters
     * @return token & user information
     */
    @PostMapping("/login")
    public ApiResponse<LoginDTO> login(@RequestBody LoginParam loginParam) {
        LoginDTO loginDTO = loginService.login(loginParam);
        return new ApiResponse<>(true, "Authentication successful", loginDTO);
    }

    /**
     * 토큰 재발행
     * @param loginParam login parameters
     * @return token & user information
     */
    @PostMapping("/reissue")
    public ApiResponse<LoginDTO> refreshToken(@RequestBody LoginParam loginParam) {
        LoginDTO loginDTO = loginService.reissue(loginParam);
        return new ApiResponse<>(true, "Token refreshed successfully", loginDTO);
    }

    /**
     * 로그아웃
     * @param loginParam login parameters
     * @return message
     */
    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestBody LoginParam loginParam) {
        Boolean result = loginService.logout(loginParam);
        String message = "Logout successful";
        if(!result) {
            message = "Logout failed";
        }
        return new ApiResponse<>(result, message, null);
    }

    /**
     * 회원가입
     * @param userDTO signUp parameters
     * @return message
     */
    @PostMapping("/signUp")
    public ApiResponse<String> signUp(@RequestBody UserDTO userDTO){
        loginService.signUp(userDTO);
        return new ApiResponse<>(true, "Your account has been created", null);
    }

}
