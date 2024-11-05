package com.MOA.backend.global.auth.OAuth2.controller;

import com.MOA.backend.domain.user.dto.UserSignupRequestDto;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.service.UserService;
import com.MOA.backend.global.auth.jwt.service.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "회원가입 테스트용", description = "테스트를 위해 임의로 구현한 로그인입니다.")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserSignupRequestDto userDto) {
        // 사용자를 회원가입 시킴
        User createdUser = userService.signup(userDto);

        // 생성된 사용자의 ID로 JWT 토큰을 발급
        String token = jwtUtil.generateAccessToken(createdUser.getUserId());

        // 응답으로 JWT 토큰을 JSON 형식으로 반환
        return ResponseEntity.ok()
                .header("AuthorizationJWT", "Bearer " + token)
                .body(Collections.singletonMap("token", token));
    }


}
