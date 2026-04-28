package com.lollipop.board.setup.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lollipop.board.setup.advice.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;

public class JwtUtils {

    public static void setErrorResponse(HttpServletRequest request, HttpServletResponse response, int statusCode, String message, String errorCode) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .errorCode(errorCode)
                .requestUrl(request.getRequestURI())
                .method(request.getMethod())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String errorMessage = objectMapper.writeValueAsString(errorResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        response.getWriter().write(errorMessage);
        response.getWriter().flush();
        response.getWriter().close();
    }


}
