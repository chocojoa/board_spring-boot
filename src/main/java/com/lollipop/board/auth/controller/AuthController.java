package com.lollipop.board.auth.controller;

import com.lollipop.board.common.model.ApiResponse;
import com.lollipop.board.auth.model.AuthDTO;
import com.lollipop.board.auth.model.AuthParam;
import com.lollipop.board.auth.service.AuthService;
import com.lollipop.board.admin.user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인
     *
     * @param authParam 로그인 정보
     * @return 토큰 및 사용자 정보
     */
    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse<AuthDTO>> signIn(@RequestBody AuthParam authParam) {
        AuthDTO authDTO = authService.signIn(authParam);
        return ResponseEntity.ok().body(ApiResponse.success(authDTO));
    }

    /**
     * 토큰 재발행
     *
     * @param authParam 로그인 정보
     * @return 토큰 및 사용자 정보
     */
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<AuthDTO>> refreshToken(@RequestBody AuthParam authParam) {
        AuthDTO authDTO = authService.reissue(authParam);
        return ResponseEntity.ok().body(ApiResponse.success(authDTO));
    }

    /**
     * 로그아웃
     *
     * @param authParam 로그인 정보
     * @return 로그아웃 정보
     */
    @PostMapping("/signOut")
    public ResponseEntity<ApiResponse<Void>> signOut(@RequestBody AuthParam authParam) {
        authService.signOut(authParam);
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
        authService.signUp(userDTO);
        return ResponseEntity.ok().body(ApiResponse.success(null));
    }

}
