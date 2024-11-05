package com.lollipop.board.common.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.lollipop.board.common.jwt.JwtUtils.setErrorResponse;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.error(accessDeniedException.getMessage(), accessDeniedException);
        String message = "권한이 부족하여 요청이 거부되었습니다.";
        setErrorResponse(request, response, HttpServletResponse.SC_UNAUTHORIZED, message, null);
    }
}
