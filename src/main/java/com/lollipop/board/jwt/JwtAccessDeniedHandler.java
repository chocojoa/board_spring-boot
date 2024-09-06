package com.lollipop.board.jwt;

import com.lollipop.board.advice.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.error(accessDeniedException.getMessage(), accessDeniedException);
        String message = "권한이 부족하여 요청이 거부되었습니다.";
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .requestUrl(request.getRequestURI())
                .method(request.getMethod())
                .build();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(errorResponse.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }
}
