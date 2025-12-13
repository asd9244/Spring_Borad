package com.board.configuration;

import com.board.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new AuthInterceptor())

                // 보호할 URL 패턴
                .addPathPatterns(
                        "/board/**",
                        "/comments/**",
                        "/admin/**"
                )

                // 예외(접근 허용) URL
                .excludePathPatterns(
                        "/login.do",
                        "/logout.do",
                        "/member/register.do",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/error"
                );
    }
}