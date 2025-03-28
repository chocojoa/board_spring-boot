package com.lollipop.board.common.controller;

import com.lollipop.board.admin.menu.model.MenuDTO;
import com.lollipop.board.admin.menu.service.MenuService;
import com.lollipop.board.setup.model.ApiResponse;
import com.lollipop.board.common.model.UserMenuDTO;
import com.lollipop.board.common.service.UserMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/menus")
public class UserMenuController {

    private final UserMenuService userMenuService;

    private final MenuService menuService;

    /**
     * 네비게이션 메뉴 목록 조회
     *
     * @param menuName 메뉴 조회
     * @return 메뉴 목록
     */
    @GetMapping("/breadcrumbs")
    public ResponseEntity<ApiResponse<List<MenuDTO>>> retrieveBreadcrumbs(String menuName) {
        List<MenuDTO> breadcrumbs = menuService.selectBreadcrumbs(menuName);
        return ResponseEntity.ok(ApiResponse.success(breadcrumbs));
    }

    /**
     * 사용자 메뉴 목록 조회
     * @param userId 사용자 아이디
     * @return 사용자 메뉴 목록
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<UserMenuDTO>>> retrieveUserMenuList(@PathVariable Integer userId) {
        List<UserMenuDTO> userMenuList = userMenuService.retrieveUserMenuList(userId);
        return new ResponseEntity<>(ApiResponse.success(userMenuList), HttpStatus.OK);
    }
}
