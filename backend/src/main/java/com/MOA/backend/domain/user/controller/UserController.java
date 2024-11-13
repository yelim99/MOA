package com.MOA.backend.domain.user.controller;

import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.image.service.S3Service;
import com.MOA.backend.domain.user.dto.DeviceTokenRequest;
import com.MOA.backend.domain.user.dto.UserResponse;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.service.UserService;
import com.MOA.backend.global.auth.jwt.service.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "User", description = "유저 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final S3Service s3Service;

    @Operation(summary = "유저 정보 수정", description = "JWT 토큰을 통해 유저 정보를 수정합니다.")
    @PutMapping
    public ResponseEntity<User> updateUser(
            @Parameter(description = "JWT 토큰", required = true)
            @RequestHeader("Authorization") String token,
            @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(jwtUtil.extractUserId(token), userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "유저 정보 상세조회", description = "JWT 토큰을 통해 유저 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "JWT 토큰", required = true)
            @RequestHeader("Authorization") String token) {
        Optional<User> user = userService.findByUserId(jwtUtil.extractUserId(token));
        return user.map(u -> {
                    String userProfileImage = s3Service.getUserProfile(u.getUserEmail());
                    UserResponse userResponse = new UserResponse(u, userProfileImage);
                    return ResponseEntity.ok(userResponse);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "유저가 포함된 그룹들 조회", description = "유저가 속한 그룹 목록을 조회합니다.")
    @GetMapping("/groups")
    public List<Group> getMyGroups(
            @Parameter(description = "JWT 토큰", required = true)
            @RequestHeader("Authorization") String token) {
        return userService.getUserGroups(jwtUtil.extractUserId(token));
    }

    @PutMapping("/device-token")
    public ResponseEntity<?> updateDeviceToken(@RequestBody DeviceTokenRequest deviceTokenRequest) {
        userService.updateDeviceToken(deviceTokenRequest.getUserId(), deviceTokenRequest.getDeviceToken());
        return ResponseEntity.ok("디바이스 토큰이 등록되었습니다.");
    }
}
