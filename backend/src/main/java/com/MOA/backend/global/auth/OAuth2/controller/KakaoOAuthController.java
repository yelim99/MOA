package com.MOA.backend.global.auth.OAuth2.controller;

import com.MOA.backend.global.auth.OAuth2.service.KakaoOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoOAuthController {
    private final KakaoOAuthService kakaoOAuthService;

    @PostMapping
    public ResponseEntity<?> kakaoLogin(@RequestHeader("Authorization") String accessToken) {
        String token = accessToken.replace("Bearer ", "");
        String jwtToken = kakaoOAuthService.processUser(token);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", "Bearer " + jwtToken);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("로그인 성공");
    }
}
