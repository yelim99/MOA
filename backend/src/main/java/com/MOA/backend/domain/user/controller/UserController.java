package com.MOA.backend.domain.user.controller;

import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.service.UserService;
import com.MOA.backend.global.auth.jwt.service.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // 유저 정보 수정
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String token, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(jwtUtil.extractUserId(jwtUtil.remove(token)), userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // 유저 정보 상세조회
    @GetMapping
    public ResponseEntity<User> getUserById(@RequestHeader("Authorization") String token) {
        Optional<User> user = userService.findByUserId(jwtUtil.extractUserId(token));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 유저가 포함된 그룹들 조회
    @GetMapping("/groups")
    public List<Group> getMyGroups(@RequestHeader("Authorization") String token) {
        return userService.getUserGroups(jwtUtil.extractUserId(jwtUtil.remove(token)));
    }

    @GetMapping("/generateToken")
    public ResponseEntity<String> generateTestToken(@RequestParam String userEmail, @RequestParam Long userId) {
        // 임의의 유저 ID로 JWT 토큰 생성
        String token = jwtUtil.generateAccessToken(userEmail, userId);
        return ResponseEntity.ok("Bearer " + token);
    }
}
