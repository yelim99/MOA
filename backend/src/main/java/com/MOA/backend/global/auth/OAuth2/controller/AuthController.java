package com.MOA.backend.global.auth.OAuth2.controller;

import com.MOA.backend.domain.user.dto.UserSignupRequestDto;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.service.UserService;
import com.MOA.backend.global.auth.jwt.service.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "회원가입 테스트용", description = "테스트를 위해 임의로 구현한 회원가입입니다.")
    @PostMapping("/login")
    public ResponseEntity<?> CreateUser(@RequestBody UserSignupRequestDto userDto) {
        try {
            // 사용자를 회원가입 시킴
            if (!userService.findByUserEmail(userDto.getEmail()).isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "이미 사용 중인 이메일입니다."));
            }

            userService.signup(userDto);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "회원가입이 완료되었습니다."));

        } catch (Exception e) {
            // 예외 발생 시 에러 메시지와 함께 400 Bad Request 응답 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "회원가입에 실패했습니다."));
        }
    }

    @Operation(summary = "로그인 테스트용", description = "테스트를 위해 임의로 구현한 로그인입니다.")
    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String userEmail) {
        User findUser = userService.findByUserEmail(userEmail).orElse(null);

        if(findUser == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "사용자를 찾을 수 없습니다."));
        }

        String token = jwtUtil.generateAccessToken(findUser.getUserId());

        return ResponseEntity.ok()
                .body(Collections.singletonMap("token", "Bearer " + token));
    }


}
