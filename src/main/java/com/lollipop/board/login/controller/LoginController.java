package com.lollipop.board.login.controller;

import com.lollipop.board.common.model.ApiResponse;
import com.lollipop.board.login.model.LoginDTO;
import com.lollipop.board.login.model.LoginParam;
import com.lollipop.board.login.service.LoginService;
import com.lollipop.board.admin.user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    /**
     * 로그인
     *
     * @param loginParam 로그인 정보
     * @return 토큰 및 사용자 정보
     */
    @PostMapping("signIn")
    public ResponseEntity<ApiResponse<LoginDTO>> signIn(@RequestBody LoginParam loginParam) {
        LoginDTO loginDTO = loginService.signIn(loginParam);
        return ResponseEntity.ok().body(ApiResponse.success(loginDTO));
    }

    /**
     * 토큰 재발행
     *
     * @param loginParam 로그인 정보
     * @return 토큰 및 사용자 정보
     */
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<LoginDTO>> refreshToken(@RequestBody LoginParam loginParam) {
        LoginDTO loginDTO = loginService.reissue(loginParam);
        return ResponseEntity.ok().body(ApiResponse.success(loginDTO));
    }

    /**
     * 로그아웃
     *
     * @param loginParam 로그인 정보
     * @return 로그아웃 정보
     */
    @PostMapping("/signOut")
    public ResponseEntity<ApiResponse<Void>> signOut(@RequestBody LoginParam loginParam) {
        loginService.signOut(loginParam);
        return ResponseEntity.ok().body(ApiResponse.success(null));
    }

    /**
     * 회원가입
     *
     * @param userDTO 회원가입 정보
     * @return message
     */
    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody UserDTO userDTO) {
        loginService.signUp(userDTO);
        return ResponseEntity.ok().body(ApiResponse.success(null));
    }

}
