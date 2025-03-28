package com.lollipop.board.common.controller;

import com.lollipop.board.admin.user.model.UserDTO;
import com.lollipop.board.setup.model.ApiResponse;
import com.lollipop.board.common.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/profile")
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> updateProfile(@PathVariable Integer userId, @RequestBody UserDTO userDTO) {
        userDTO.setUserId(userId);
        profileService.updateProfile(userDTO);
        return new ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
    }

}
