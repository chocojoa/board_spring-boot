package com.lollipop.board.setup.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.lollipop.board.setup.jwt.JwtUtils.setErrorResponse;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error(authException.getMessage(), authException);
        // API 요청일 경우에만 JSON 응답
        if (request.getRequestURI().startsWith("/rest/")) {
            String message = "인증되지 않았거나 유효한 자격 증명이 부족하여 요청이 거부되었습니다.";
            setErrorResponse(request, response, HttpServletResponse.SC_UNAUTHORIZED, message, null);
        } else {
            request.getRequestDispatcher("/index.html").forward(request, response);
        }
    }
}
