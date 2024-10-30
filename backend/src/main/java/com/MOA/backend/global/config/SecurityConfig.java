package com.MOA.backend.global.config;

import com.MOA.backend.global.auth.OAuth2.service.CustomOAuth2UserService;
import com.MOA.backend.global.auth.jwt.service.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtUtil jwtUtil; // JWT 생성 유틸리티 주입

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/oauth2/**", "/login/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .successHandler((request, response, authentication) -> {
                                    // JWT 토큰 생성 및 응답
                                    String email = authentication.getName(); // 사용자의 이메일을 가져옴
                                    String jwtToken = jwtUtil.generateAccessToken(email); // JWT 토큰 생성

                                    response.setContentType("application/json;charset=UTF-8");
                                    response.getWriter().write("{\"token\":\"" + jwtToken + "\"}");
                                })
                                .failureUrl("/loginFailure") // 로그인 실패 시 이동할 URL
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint.userService(customOAuth2UserService) // 사용자 정보 핸들링 서비스
                                )
                );
        return http.build();
    }
}
