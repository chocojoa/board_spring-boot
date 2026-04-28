package com.lollipop.board.setup.config;

import com.lollipop.board.setup.interceptor.MenuAuthorizationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final MenuAuthorizationInterceptor menuAuthorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> excludePathPatterns = Arrays.asList(
                "/api/common/**",
                "/api/auth/**",
                "/",
                "/error"
        );

        registry.addInterceptor(menuAuthorizationInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(excludePathPatterns);
    }

}
