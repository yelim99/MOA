package com.MOA.backend.domain.user.controller;

import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.group.service.GroupService;
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
    private final GroupService groupService;
    private final JwtUtil jwtUtil;

    // 유저 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorizaion") String token,
                                           @RequestBody User userDetails) {
        Long userId = jwtUtil.extractUserId(jwtUtil.remove(token));
        User updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // 유저 정보 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@RequestHeader("Authorizaion") String token) {
        Long userId = jwtUtil.extractUserId(jwtUtil.remove(token));
        Optional<User> user = userService.finfByUserId(userId);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 유저가 포함된 그룹들 조회
    @GetMapping("/groups")
    public List<Group> getMyGroups(@RequestHeader("Authorizaion") String token) {
        Long userId = jwtUtil.extractUserId(jwtUtil.remove(token));
        return userService.getUserGroups(userId);
    }
}
