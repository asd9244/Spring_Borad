package com.board.configuration;

import com.board.interceptor.AuthInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// WebMvcConfigurer: Spring MVC의 기본 설정을 사용자 입맛에 맞게 커스터마이징하기 위해 구현하는 인터페이스
@Configuration
public class WebConfig implements WebMvcConfigurer {
        
        @Bean // 인터셉터 자체를 빈으로 등록 (필요 시 의존성 주입 가능)
        public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
        }

    // addInterceptors == WebMvcConfigurer 인터페이스에 정의된 메서드, 인터셉터를 등록(Registry)할 때 호출되는 콜백 메서드.
    // 실행 시점: 서버가 시작될 때 스프링이 이 메서드를 자동으로 호출하여, 등록한 규칙들을 메모리에 저장
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // InterceptorRegistry registry == spring이 제공하는 인터셉터 등록 관리 객체.
        // 이 객체에 addInterceptor()를 통해 인터셉터 객체를 추가하면, 스프링이 나중에 요청이 들어올 때마다 이 객체를 확인하고 검사를 수행

        // 계속 .을 찍어서 메서드를 계속 연결하는 패턴으로 진행 == 빌더 패턴(Builder Pattern)
        // registry.addInterceptor == AuthInterceptor 객체를 메모리에 생성하고, Spring 관리 명단에 올림.
        registry.addInterceptor(new AuthInterceptor())


                // Ant Path Pattern 문법 (/**) == 해당 경로 아래의 모든 하위경로를 포함.
                // 1. 모든 경로에 대해 인터셉터 적용
                .addPathPatterns("/**")

                // 예외(접근 허용) URL
                // 2. 단, 아래 경로는 예외 처리 (로그인 없이 접근 가능해야 함
                .excludePathPatterns(
        "/login.do",            // 로그인 페이지
                        "/logout.do",           // 로그아웃
                        "/members/add.do",      // 회원가입 화면
                        "/members/save.do",      // 회원가입 처리
                        "/board/list.do",       // [추가] 게시판 목록 (누구나 볼 수 있음)
                        "/board/view.do",       // [추가] 게시글 상세 (누구나 볼 수 있음)
                        "/css/**",              // 정적 리소스
                        "/scripts/**",
                        "/plugin/**",
                        "/fonts/**",
                        "/images/**",
                        "/favicon.ico",
                        "/error"
                );
    }
}