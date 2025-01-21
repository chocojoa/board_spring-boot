package com.lollipop.board.common.security;

import com.lollipop.board.common.jwt.JwtAccessDeniedHandler;
import com.lollipop.board.common.jwt.JwtAuthenticationEntryPoint;
import com.lollipop.board.common.jwt.JwtAuthenticationFilter;
import com.lollipop.board.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.formLogin(AbstractHttpConfigurer::disable);

        HttpSecurity httpSecurity = http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/", "/api/auth/**", "/error").permitAll()
                .requestMatchers("/index.html", "/images/**", "/assets/**").permitAll()
                .requestMatchers("/*.js", "/*.json", "/*.ico", "/*.png", "/*.svg", "/*.css").permitAll()
                .anyRequest().authenticated());

        HttpSecurity exceptionHandling = httpSecurity.exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint));

        exceptionHandling.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
