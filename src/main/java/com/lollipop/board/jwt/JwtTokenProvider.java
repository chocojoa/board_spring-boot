package com.lollipop.board.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey key;

    @Value("${jwt.accessTokenExpirationMs}")
    private long accessTokenExpirationTime;

    @Value("${jwt.refreshTokenExpirationMs}")
    private long refreshTokenExpirationTime;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        this.key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public JwtToken generateToken(Authentication authentication) {
        var authorities = getAuthoritiesString(authentication);
        String accessToken = generateTokenWithClaims(authentication.getName(), authorities, accessTokenExpirationTime);

        String refreshToken = generateTokenWithClaims(null, null, refreshTokenExpirationTime);

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        var authorities = getAuthoritiesCollection(claims.get("auth"));

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(accessToken).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private String generateTokenWithClaims(String subject, String claim, long expirationTime) {
        var jwtBuilder = Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key);

        if (subject != null) {
            jwtBuilder.subject(subject);
        }

        if (claim != null) {
            jwtBuilder.claim("auth", claim);
        }

        return jwtBuilder.compact();
    }

    private String getAuthoritiesString(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    private Collection<? extends GrantedAuthority> getAuthoritiesCollection(Object claim) {
        if (claim == null) {
            throw new IllegalArgumentException("권한 정보가 없는 토큰 입니다.");
        }

        return Arrays.stream(claim.toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}

