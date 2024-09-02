package com.lollipop.board.login.service;

import com.lollipop.board.jwt.JwtToken;
import com.lollipop.board.jwt.JwtTokenProvider;
import com.lollipop.board.login.model.LoginDTO;
import com.lollipop.board.login.model.LoginParam;
import com.lollipop.board.redis.dao.RedisDAO;
import com.lollipop.board.user.mapper.UserMapper;
import com.lollipop.board.user.model.UserDTO;
import com.lollipop.board.user.model.UserParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final RedisDAO redisDAO;

    @Value("${jwt.refreshTokenExpirationMs}")
    private long refreshTokenExpirationTime;

    public LoginDTO login(LoginParam loginParam) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginParam.getEmail(), loginParam.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities = jwtTokenProvider.getAuthoritiesCollection(authentication);
        UserDetails userDetails = new User(loginParam.getEmail(), "", authorities);

        return getToken(userDetails.getUsername());
    }

    public LoginDTO reissue(LoginParam loginParam) {
        try {
            String refreshToken = loginParam.getRefreshToken();

            if (validateRefreshToken(loginParam.getEmail(), refreshToken)) {
                String username = jwtTokenProvider.extractUsername(refreshToken);

                UserParam userParam = UserParam.builder().email(username).build();
                UserDTO userDTO = userMapper.selectUserByEmail(userParam);

                Collection<? extends GrantedAuthority> authorities = jwtTokenProvider.extractAuth(refreshToken);
                UserDetails userDetails = new User(userDTO.getEmail(), "", authorities);

                return getReissueToken(userDetails.getUsername(), refreshToken);
            } else {
                throw new RuntimeException("Invalid refresh token");
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid refresh token");
        }
    }

    public void signUp(UserDTO userDTO) {
        try {
            UserParam userParam = UserParam.builder().email(userDTO.getEmail()).build();
            UserDTO user = userMapper.selectUserByEmail(userParam);

            if(user == null) {
                userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
                userMapper.insertUser(userDTO);
            }
        } catch(Exception e) {
            throw new RuntimeException("sign up error");
        }
    }

    private LoginDTO getToken(String username) {

        String accessToken = jwtTokenProvider.generateAccessToken(username);
        String refreshToken = jwtTokenProvider.generateRefreshToken(username);

        JwtToken token = JwtToken.builder().grantType("Bearer").accessToken(accessToken).refreshToken(refreshToken).build();

        redisDAO.setValues(username, refreshToken, Duration.ofMillis(refreshTokenExpirationTime));

        UserParam userParam = UserParam.builder().email(username).build();
        UserDTO user = userMapper.selectUserByEmail(userParam);

        return LoginDTO.builder().token(token).user(user).build();
    }

    private LoginDTO getReissueToken(String username, String refreshToken) {

        String accessToken = jwtTokenProvider.generateAccessToken(username);
        JwtToken token = JwtToken.builder().grantType("Bearer").accessToken(accessToken).refreshToken(refreshToken).build();
        UserParam userParam = UserParam.builder().email(username).build();
        UserDTO user = userMapper.selectUserByEmail(userParam);

        return LoginDTO.builder().token(token).user(user).build();
    }

    public boolean validateRefreshToken(String username, String refreshToken) {
        return redisDAO.getValues(username).equals(refreshToken);
    }

    public Boolean logout(LoginParam loginParam) {
        String username = jwtTokenProvider.extractUsername(loginParam.getRefreshToken());
        return redisDAO.deleteValues(username);
    }
}
