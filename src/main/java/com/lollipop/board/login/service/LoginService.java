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
import java.util.HashMap;
import java.util.Map;

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

            if (validateRefreshToken(refreshToken)) {
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

    public ResponseEntity<?> signUp(UserDTO userDTO) {
        try {
            UserParam userParam = UserParam.builder().email(userDTO.getEmail()).build();
            UserDTO user = userMapper.selectUserByEmail(userParam);

            boolean status = false;
            String message = "이미 등록된 계정이 있습니다.";

            if(user == null) {
                userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
                userMapper.insertUser(userDTO);

                status = true;
                message = "계정이 생성되었습니다.";
            }
            Map<String, Object> result = new HashMap<>();
            result.put("status", status);
            result.put("message", message);
            return ResponseEntity.ok().body(result);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private LoginDTO getToken(UserDetails userDetails) {

        String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        JwtToken token = JwtToken.builder().grantType("Bearer").accessToken(accessToken).refreshToken(refreshToken).build();

        redisDAO.setValues(refreshToken, userDetails.getUsername(), Duration.ofMillis(refreshTokenExpirationTime));

        UserParam userParam = UserParam.builder().email(userDetails.getUsername()).build();
        UserDTO user = userMapper.selectUserByEmail(userParam);

        return LoginDTO.builder().token(token).user(user).build();
    }

    public boolean validateRefreshToken(String token) {
        try {
            String username = jwtTokenProvider.extractUsername(token);
            // Redis에서 Refresh Token 검증
            String storedUsername = redisDAO.getValues(token);
            return username.equals(storedUsername);
        } catch (Exception e) {
            log.info("validateRefreshToken error : " + e);
            return false;
        }
    }

}
