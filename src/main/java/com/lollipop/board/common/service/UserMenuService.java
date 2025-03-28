package com.lollipop.board.common.service;

import com.lollipop.board.common.mapper.MenuApiMapper;
import com.lollipop.board.common.mapper.UserMenuMapper;
import com.lollipop.board.common.model.MenuApiDTO;
import com.lollipop.board.common.model.UserMenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMenuService {

    private final UserMenuMapper userMenuMapper;

    private final MenuApiMapper menuApiMapper;

    public List<UserMenuDTO> retrieveUserMenuList(Integer userId) {
        return userMenuMapper.selectUserMenuList(userId);
    }

    @Cacheable(value = "userMenuUrlCache", key = "#email")
    public List<String> getAccessibleUrls(String email) {
        List<MenuApiDTO> menuApiDTOList = menuApiMapper.selectMenuApiList(email);
        return menuApiDTOList.stream().map(MenuApiDTO::getApiUrl).toList();
    }
}
