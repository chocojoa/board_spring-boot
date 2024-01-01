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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> login(LoginParam loginParam) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginParam.getEmail(), loginParam.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities = jwtTokenProvider.getAuthoritiesCollection(authentication);
        UserDetails userDetails = new User(loginParam.getEmail(), "", authorities);

        LoginDTO tokens = getToken(userDetails);
        return ResponseEntity.ok(tokens);
    }

    public ResponseEntity<?> reissue(LoginParam loginParam) {
        try {
            String refreshToken = loginParam.getRefreshToken();

            if (jwtTokenProvider.validateToken(refreshToken)) {
                String username = jwtTokenProvider.extractUsername(refreshToken);

                UserParam userParam = UserParam.builder().email(username).build();
                UserDTO userDTO = userMapper.selectUserByEmail(userParam);

                Collection<? extends GrantedAuthority> authorities = jwtTokenProvider.extractAuth(refreshToken);
                UserDetails userDetails = new User(userDTO.getEmail(), "", authorities);

                LoginDTO tokens = getToken(userDetails);
                return ResponseEntity.ok(tokens);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public void signUp(UserDTO userDTO) {
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userMapper.insertUser(userDTO);
    }

    private LoginDTO getToken(UserDetails userDetails) {

        String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        JwtToken token = JwtToken.builder().grantType("Bearer").accessToken(accessToken).refreshToken(refreshToken).build();

        redisDAO.setValues(userDetails.getUsername(), refreshToken, Duration.ofMillis(refreshTokenExpirationTime));

        UserParam userParam = UserParam.builder().email(userDetails.getUsername()).build();
        UserDTO user = userMapper.selectUserByEmail(userParam);

        return LoginDTO.builder().token(token).user(user).build();
    }

}
