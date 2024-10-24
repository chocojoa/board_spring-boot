package com.lollipop.board.security.service;

import com.lollipop.board.admin.user.mapper.UserMapper;
import com.lollipop.board.admin.user.model.UserDTO;
import com.lollipop.board.admin.user.model.UserParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserParam userParam = UserParam.builder().email(username).build();
        UserDTO user = userMapper.selectUserByEmail(userParam);

        if (user != null) {
            return createUserDetails(user);
        } else {
            String errorMessage = getNotFoundExceptionMessage(username);
            log.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }
    }

    private String getNotFoundExceptionMessage(String username) {
        return "Cannot find user: " + username;
    }

    private UserDetails createUserDetails(UserDTO userDTO) {
        return new User(userDTO.getEmail(), userDTO.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
    }
}
