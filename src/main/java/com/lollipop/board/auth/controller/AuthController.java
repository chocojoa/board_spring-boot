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
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
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
     * @param response 로그인 정보
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
     * @param response 로그인 정보
     * @return 로그아웃 정보
     */
    @PostMapping("/signOut")
    public ResponseEntity<ApiResponse<Void>> signOut(@CookieValue(value = "refreshToken", required = false) String refreshTokenString, HttpServletResponse response) {
        authService.signOut(refreshTokenString);
        clearTokenCookies(response);
        return ResponseEntity.ok().body(ApiResponse.success(null));
    }

    /**
     * 토큰 쿠키 설정
     * @param response 응답 객체
     * @param authDTO 인증 정보 객체
     */
    private void setTokenCookies(HttpServletResponse response, AuthDTO authDTO) {
        if (authDTO != null && authDTO.getToken() != null) {
            ResponseCookie accessCookie = ResponseCookie.from("accessToken", authDTO.getToken().getAccessToken())
                    .httpOnly(true)
                    .path("/")
                    .sameSite("Strict")
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

            ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", authDTO.getToken().getRefreshToken())
                    .httpOnly(true)
                    .path("/")
                    .sameSite("Strict")
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        }
    }

    /**
     * 토큰 쿠키 삭제
     * @param response 응답 객체
     */
    private void clearTokenCookies(HttpServletResponse response) {
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
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
