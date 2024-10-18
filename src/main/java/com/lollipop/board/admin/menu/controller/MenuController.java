package com.lollipop.board.admin.menu.controller;

import com.lollipop.board.admin.menu.model.MenuDTO;
import com.lollipop.board.admin.menu.service.MenuService;
import com.lollipop.board.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/admin/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuDTO>>> retrieveMenus() {
        return ResponseEntity.ok(ApiResponse.success(menuService.retrieveMenus()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MenuDTO>> createMenu(@RequestBody MenuDTO menuDTO) {
        return ResponseEntity.ok(ApiResponse.success(menuService.createMenu(menuDTO)));
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<ApiResponse<MenuDTO>> modifyMenu(@PathVariable Integer menuId, @RequestBody MenuDTO menuDTO) {
        menuDTO.setMenuId(menuId);
        return ResponseEntity.ok(ApiResponse.success(menuService.modifyMenu(menuDTO)));
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<ApiResponse<Void>> deleteMenu(@PathVariable Integer menuId) {
        menuService.removeMenu(menuId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
