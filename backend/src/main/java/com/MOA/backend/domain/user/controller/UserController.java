package com.MOA.backend.domain.user.controller;

import com.MOA.backend.domain.user.dto.UserSignupRequestDto;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.service.UserService;
import com.MOA.backend.global.auth.jwt.service.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public Mono<ResponseEntity<String>> signup(@RequestBody UserSignupRequestDto userSignupRequestDto) {
        return Mono.fromCallable(() -> {
            userService.signup(userSignupRequestDto);
            String jwtToken = jwtUtil.generateAccessToken(userSignupRequestDto.getEmail());
            return ResponseEntity.ok("회원가입이 완료되었습니다: JWT토큰 - " + jwtToken);
        });
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody String userEmail) {
        return Mono.fromCallable(() -> {
            Optional<User> existingUser = userService.findByUserEmail(userEmail);

            if (existingUser.isPresent()) {
                User user = existingUser.get();
                String jwtToken = jwtUtil.generateAccessToken(user.getUserEmail());
                return ResponseEntity.ok("로그인 성공: JWT토큰 - " + jwtToken);
            } else {
                return ResponseEntity.status(403).body("카카오로 회원가입된 아이디가 없습니다. 가입하시겠습니까?");
            }
        });
    }


}
