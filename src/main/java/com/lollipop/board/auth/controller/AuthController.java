package com.lollipop.board.auth.controller;

import com.lollipop.board.setup.model.ApiResponse;
import com.lollipop.board.auth.model.AuthDTO;
import com.lollipop.board.auth.model.AuthParam;
import com.lollipop.board.auth.service.AuthService;
import com.lollipop.board.admin.user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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
    public ResponseEntity<ApiResponse<AuthDTO>> signIn(@RequestBody AuthParam authParam, HttpServletResponse response) {
        AuthDTO authDTO = authService.signIn(authParam);
        setTokenCookies(response, authDTO);
        return ResponseEntity.ok().body(ApiResponse.success(authDTO));
    }

    /**
     * 토큰 재발행
     *
     * @param authParam 로그인 정보
     * @return 토큰 및 사용자 정보
     */
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<AuthDTO>> refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshTokenString, HttpServletResponse response) {
        AuthParam authParam = new AuthParam();
        authParam.setRefreshToken(refreshTokenString);
        AuthDTO authDTO = authService.reissue(authParam);
        setTokenCookies(response, authDTO);
        return ResponseEntity.ok().body(ApiResponse.success(authDTO));
    }

    /**
     * 로그아웃
     *
     * @param authParam 로그인 정보
     * @return 로그아웃 정보
     */
    @PostMapping("/signOut")
    public ResponseEntity<ApiResponse<Void>> signOut(@CookieValue(value = "refreshToken", required = false) String refreshTokenString, HttpServletResponse response) {
        authService.signOut(refreshTokenString);
        clearTokenCookies(response);
        return ResponseEntity.ok().body(ApiResponse.success(null));
    }

    private void setTokenCookies(HttpServletResponse response, AuthDTO authDTO) {
        if (authDTO != null && authDTO.getToken() != null) {
            Cookie accessCookie = new Cookie("accessToken", authDTO.getToken().getAccessToken());
            accessCookie.setHttpOnly(true);
            accessCookie.setPath("/");
            response.addCookie(accessCookie);

            Cookie refreshCookie = new Cookie("refreshToken", authDTO.getToken().getRefreshToken());
            refreshCookie.setHttpOnly(true);
            refreshCookie.setPath("/");
            response.addCookie(refreshCookie);
        }
    }

    private void clearTokenCookies(HttpServletResponse response) {
        Cookie accessCookie = new Cookie("accessToken", "");
        accessCookie.setPath("/");
        accessCookie.setMaxAge(0);
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refreshToken", "");
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0);
        response.addCookie(refreshCookie);
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
