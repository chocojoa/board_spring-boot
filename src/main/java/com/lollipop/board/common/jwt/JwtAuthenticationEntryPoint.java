package com.lollipop.board.common.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.lollipop.board.common.jwt.JwtUtils.setErrorResponse;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error(authException.getMessage(), authException);
        if (request.getHeader("Content-Type").contains("application/json")) {
            String message = "인증되지 않았거나 유효한 자격 증명이 부족하여 요청이 거부되었습니다.";
            setErrorResponse(request, response, HttpServletResponse.SC_UNAUTHORIZED, message, null);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
