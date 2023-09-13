package com.lollipop.board.login.service;

import com.lollipop.board.jwt.JwtToken;
import com.lollipop.board.jwt.JwtTokenProvider;
import com.lollipop.board.login.model.LoginDTO;
import com.lollipop.board.login.model.LoginParam;
import com.lollipop.board.user.mapper.UserMapper;
import com.lollipop.board.user.model.UserDTO;
import com.lollipop.board.user.model.UserParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    public LoginDTO login(LoginParam loginParam) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginParam.getEmail(), loginParam.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtToken token = jwtTokenProvider.generateToken(authentication);

        UserParam userParam = new UserParam();
        userParam.setEmail(loginParam.getEmail());
        UserDTO user = userMapper.selectUserByEmail(userParam);
        return LoginDTO.builder().token(token).user(user).build();
    }

    public void signUp(UserDTO userDTO) {
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userMapper.insertUser(userDTO);
    }
}
