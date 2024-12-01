package com.lollipop.board.common.controller;

import com.lollipop.board.common.model.ApiResponse;
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
@RequestMapping("/api/common/userMenu")
public class UserMenuController {

    private final UserMenuService userMenuService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<UserMenuDTO>>> retrieveUserMenuList(@PathVariable Integer userId) {
        List<UserMenuDTO> userMenuList = userMenuService.retrieveUserMenuList(userId);
        return new ResponseEntity<>(ApiResponse.success(userMenuList), HttpStatus.OK);
    }
}
