package com.lollipop.board.auth.service;

import com.lollipop.board.admin.role.mapper.UserRoleMapper;
import com.lollipop.board.admin.role.model.UserRoleDTO;
import com.lollipop.board.admin.user.model.LoginUserDTO;
import com.lollipop.board.common.jwt.JwtToken;
import com.lollipop.board.common.jwt.JwtTokenProvider;
import com.lollipop.board.auth.model.AuthDTO;
import com.lollipop.board.auth.model.AuthParam;
import com.lollipop.board.common.redis.dao.RedisDAO;
import com.lollipop.board.admin.user.mapper.UserMapper;
import com.lollipop.board.admin.user.model.UserDTO;
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
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisDAO redisDAO;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    @Value("${jwt.refreshTokenExpirationTime}")
    private long refreshTokenExpirationTime;

    public AuthDTO signIn(AuthParam authParam) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authParam.getEmail(), authParam.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities = jwtTokenProvider.getAuthoritiesCollection(authentication);
        UserDetails userDetails = new User(authParam.getEmail(), "", authorities);

        return getToken(userDetails.getUsername());
    }

    public AuthDTO reissue(AuthParam authParam) {
        String refreshToken = authParam.getRefreshToken();
        if (validateRefreshToken(refreshToken)) {
            String username = jwtTokenProvider.extractUsername(refreshToken);
            LoginUserDTO userDTO = userMapper.selectUserByEmail(username);

            Collection<? extends GrantedAuthority> authorities = jwtTokenProvider.extractAuth(refreshToken);
            UserDetails userDetails = new User(userDTO.getEmail(), "", authorities);

            return getReissueToken(userDetails.getUsername(), refreshToken);
        } else {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }

    public void signUp(UserDTO userDTO) {
        LoginUserDTO user = userMapper.selectUserByEmail(userDTO.getEmail());

        if (user == null) {
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            userMapper.insertUser(userDTO);

            //일반사용자 권한추가
            Integer userId = userDTO.getUserId();
            List<Integer> addUserList = List.of(userId);
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            userRoleDTO.setRoleId(1);
            userRoleDTO.setCreatedBy(userId);
            userRoleDTO.setAddUserList(addUserList);
            userRoleMapper.insertUserRole(userRoleDTO);
        } else {
            throw new RuntimeException("이미 등록된 계정이 있습니다.");
        }
    }

    private AuthDTO getToken(String username) {

        String accessToken = jwtTokenProvider.generateAccessToken(username);
        String refreshToken = jwtTokenProvider.generateRefreshToken(username);

        JwtToken token = JwtToken.builder().grantType("Bearer").accessToken(accessToken).refreshToken(refreshToken).build();

        redisDAO.setValues("token_" + username, refreshToken, Duration.ofSeconds(refreshTokenExpirationTime));

        LoginUserDTO user = userMapper.selectUserByEmail(username);

        return AuthDTO.builder().token(token).user(user).build();
    }

    private AuthDTO getReissueToken(String username, String refreshToken) {

        String accessToken = jwtTokenProvider.generateAccessToken(username);
        JwtToken token = JwtToken.builder().grantType("Bearer").accessToken(accessToken).refreshToken(refreshToken).build();
        LoginUserDTO user = userMapper.selectUserByEmail(username);

        return AuthDTO.builder().token(token).user(user).build();
    }

    public boolean validateRefreshToken(String refreshToken) {
        if (StringUtils.hasText(refreshToken) && jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.extractUsername(refreshToken);
            String savedRefreshToken = redisDAO.getValues("token_" + username);
            if (savedRefreshToken == null) {
                return false;
            }
            return savedRefreshToken.equals(refreshToken);
        }
        return false;
    }

    public void signOut(AuthParam authParam) {
        String username = jwtTokenProvider.extractUsername(authParam.getRefreshToken());
        redisDAO.deleteValues("token_" + username);
    }
}
