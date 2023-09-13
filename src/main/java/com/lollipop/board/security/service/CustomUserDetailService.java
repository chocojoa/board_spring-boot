package com.lollipop.board.security.service;

import com.lollipop.board.user.mapper.UserMapper;
import com.lollipop.board.user.model.UserDTO;
import com.lollipop.board.user.model.UserParam;
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
        UserParam userParam = new UserParam();
        userParam.setEmail(username);
        UserDTO user = userMapper.selectUserByEmail(userParam);


        if (user != null) {
            return createUserDetails(user);
        } else {
            log.error("can not find User : {}", username);
            throw new UsernameNotFoundException("can not find User : " + username);
        }
    }

    private UserDetails createUserDetails(UserDTO userDTO) {
        return new User(userDTO.getEmail(), userDTO.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
    }
}
