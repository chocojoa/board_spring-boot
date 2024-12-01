package com.lollipop.board.common.service;

import com.lollipop.board.common.mapper.UserMenuMapper;
import com.lollipop.board.common.model.UserMenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMenuService {

    private final UserMenuMapper userMenuMapper;

    public List<UserMenuDTO> retrieveUserMenuList(Integer userId) {
        return userMenuMapper.selectUserMenuList(userId);
    }
}
