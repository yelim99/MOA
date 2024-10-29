package com.MOA.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/user/signup", "/api/user/login").permitAll()  // 회원가입, 로그인 경로 예외
                        .anyRequest().authenticated()  // 다른 요청은 인증 필요
                )
                .oauth2Login();  // OAuth2 로그인 설정을 유지

        return http.build();
    }
}
