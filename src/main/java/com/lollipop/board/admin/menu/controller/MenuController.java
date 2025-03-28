package com.lollipop.board.admin.menu.controller;

import com.lollipop.board.admin.menu.model.MenuDTO;
import com.lollipop.board.admin.menu.service.MenuService;
import com.lollipop.board.setup.model.ApiResponse;
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

    /**
     * 메뉴 목록 조회
     *
     * @return 메뉴 목록
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuDTO>>> retrieveMenus() {
        return ResponseEntity.ok(ApiResponse.success(menuService.retrieveMenus()));
    }

    /**
     * 메뉴 상세조회
     *
     * @param id 메뉴 아이디
     * @return 메뉴
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuDTO>> retrieveMenuById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(menuService.retrieveMenuById(id)));
    }

    /**
     * 메뉴 저장
     *
     * @param menuDTO 메뉴 정보
     * @return 저장된 메뉴
     */
    @PostMapping
    public ResponseEntity<ApiResponse<MenuDTO>> createMenu(@RequestBody MenuDTO menuDTO) {
        return ResponseEntity.ok(ApiResponse.success(menuService.createMenu(menuDTO)));
    }

    /**
     * 메뉴 수정
     *
     * @param menuId  메뉴 아이디
     * @param menuDTO 메뉴 정보
     * @return 수정된 메뉴
     */
    @PutMapping("/{menuId}")
    public ResponseEntity<ApiResponse<MenuDTO>> modifyMenu(@PathVariable Integer menuId, @RequestBody MenuDTO menuDTO) {
        menuDTO.setMenuId(menuId);
        return ResponseEntity.ok(ApiResponse.success(menuService.modifyMenu(menuDTO)));
    }

    /**
     * 메뉴 삭제
     *
     * @param menuId 메뉴 아이디
     * @return 성공 여부만
     */
    @DeleteMapping("/{menuId}")
    public ResponseEntity<ApiResponse<Void>> deleteMenu(@PathVariable Integer menuId) {
        menuService.removeMenu(menuId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
