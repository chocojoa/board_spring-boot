package com.lollipop.board.common.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.lollipop.board.common.jwt.JwtUtils.setErrorResponse;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (BadCredentialsException e) {
            String errorCode = null;
            switch (e.getMessage()) {
                case "토큰 서명이 잘못 되었습니다." -> errorCode = "INVALID_JWT_SIGNATURE";
                case "유효하지 않은 토큰입니다." -> errorCode = "INVALID_JWT_TOKEN";
                case "토큰이 만료 되었습니다." -> errorCode = "JWT_TOKEN_IS_EXPIRED";
                case "토큰을 지원하지 않습니다." -> errorCode = "JWT_TOKEN_IS_UNSUPPORTED";
                case "토큰 클레임 문자열이 비어 있습니다." -> errorCode = "JWT_CLAIMS_STRING_IS_EMPTY";
            }
            setErrorResponse(request, response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage(), errorCode);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
