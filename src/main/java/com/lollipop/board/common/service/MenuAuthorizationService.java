package com.lollipop.board.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuAuthorizationService {

    private final UserMenuService userMenuService;

    public boolean hasAccess(String email, String requestUrl) {
        List<String> accessibleUrls = userMenuService.getAccessibleUrls(email);

        // 완전 일치하는 경우
        if (accessibleUrls.contains(requestUrl)) {
            return true;
        }

        // 부분 일치하는 경우
        return accessibleUrls.stream().anyMatch(requestUrl::startsWith);
    }

}
