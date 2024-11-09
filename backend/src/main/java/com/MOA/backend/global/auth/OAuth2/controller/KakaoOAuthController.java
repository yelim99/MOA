package com.MOA.backend.global.auth.OAuth2.controller;

import com.MOA.backend.global.auth.OAuth2.service.KakaoOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoOAuthController {
    private final KakaoOAuthService kakaoOAuthService;

    @PostMapping
    public ResponseEntity<Map<String, String>> kakaoLogin(@RequestHeader("Authorization") String accessToken) {
        String token = accessToken.replace("Bearer ", "");
        String jwtToken = kakaoOAuthService.processUser(token);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("accessToken", jwtToken);

        return ResponseEntity.ok(responseBody);
    }

}
