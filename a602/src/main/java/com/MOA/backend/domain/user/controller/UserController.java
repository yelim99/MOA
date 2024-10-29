package com.MOA.backend.domain.user.controller;

import com.MOA.backend.domain.user.dto.UserSignupRequestDto;
import com.MOA.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public Mono<ResponseEntity<String>> signup(@RequestBody UserSignupRequestDto userSignupRequestDto){
        return Mono.fromCallable(() -> {
            userService.signup(userSignupRequestDto);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        });
    }

}
