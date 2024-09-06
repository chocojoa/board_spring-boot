package com.lollipop.board.jwt;

import com.lollipop.board.advice.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error(authException.getMessage(), authException);
        String message = "인증되지 않았거나 유효한 자격 증명이 부족하여 요청이 거부되었습니다.";

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .requestUrl(request.getRequestURI())
                .method(request.getMethod())
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(errorResponse.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }
}
