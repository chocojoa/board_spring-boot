package com.lollipop.board.common.service;

import com.lollipop.board.admin.user.model.UserDTO;
import com.lollipop.board.common.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileMapper profileMapper;

    public void updateProfile(UserDTO userDTO) {
        profileMapper.updateProfile(userDTO);
    }

}
