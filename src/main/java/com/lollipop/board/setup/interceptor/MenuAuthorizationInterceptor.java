package com.lollipop.board.setup.interceptor;

import com.lollipop.board.setup.jwt.JwtTokenProvider;
import com.lollipop.board.common.service.MenuAuthorizationService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class MenuAuthorizationInterceptor implements HandlerInterceptor {

    private final MenuAuthorizationService menuAuthorizationService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @Nullable Object handler) throws Exception {

        String email = getUserEmailFromAuthorizationHeader(request);

        String requestUri = request.getRequestURI();

        if (email != null && !menuAuthorizationService.hasAccess(email, requestUri)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "메뉴 접근 권한이 없습니다.");
            return false;
        }
        return true;
    }

    private String getUserEmailFromAuthorizationHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return jwtTokenProvider.extractUsername(token.substring(7));
        }
        return null;
    }

}
