package com.MOA.backend.domain.group.controller;

import com.MOA.backend.domain.group.dto.request.GroupCreateDto;
import com.MOA.backend.domain.group.dto.response.GroupDetailsResponse;
import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.group.service.GroupService;
import com.MOA.backend.domain.image.service.S3Service;
import com.MOA.backend.domain.moment.service.MomentService;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.service.UserService;
import com.MOA.backend.global.auth.jwt.service.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Group", description = "유저 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final MomentService momentService;
    private final S3Service s3Service;

    @Operation(summary = "그룹 생성", description = "JWT 토큰을 통해 새로운 그룹을 생성합니다.")
    @PostMapping
    public ResponseEntity<Group> createGroup(
            @Parameter(description = "JWT 토큰", required = true) @RequestHeader("AuthorizationJWT") String token,
            @Valid @RequestBody GroupCreateDto groupDto) {
        Group createdGroup = groupService.create(jwtUtil.extractUserId(jwtUtil.remove(token)), groupDto);
        return ResponseEntity.ok(createdGroup);
    }

    @Operation(summary = "그룹 상세 조회", description = "그룹 ID를 통해 특정 그룹의 상세 정보를 조회합니다.")
    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroupDetails(
            @Parameter(description = "그룹 ID", required = true) @PathVariable(name = "groupId") Long groupId) {
        Group group = groupService.getGroupById(groupId).orElseThrow();
        List<User> users = groupService.getGroupUsers(groupId);
        Map<String, Map<String, List<String>>> imagesInGroup =
                s3Service.getImagesInGroup(groupId, momentService.getMomentIds(groupId));

        return ResponseEntity.ok().body(GroupDetailsResponse.builder()
                .group(group)
                .users(users)
                .images(imagesInGroup)
                .build());
    }

    @Operation(summary = "그룹 수정", description = "그룹 ID를 통해 특정 그룹의 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(
            @Parameter(description = "그룹 ID", required = true) @PathVariable long id,
            @Valid @RequestBody GroupCreateDto groupDto) {
        Group updatedGroup = groupService.update(id, groupDto);
        return ResponseEntity.ok(updatedGroup);
    }

    @Operation(summary = "그룹 삭제", description = "그룹 ID를 통해 특정 그룹을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(
            @Parameter(description = "그룹 ID", required = true) @PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 나가기", description = "JWT 토큰을 통해 사용자가 특정 그룹에서 나갑니다.")
    @DeleteMapping("/{id}/leave")
    public ResponseEntity<Void> leaveGroup(
            @Parameter(description = "JWT 토큰", required = true) @RequestHeader("AuthorizationJWT") String token,
            @Parameter(description = "그룹 ID", required = true) @PathVariable Long id) {
        groupService.leaveGroup(jwtUtil.extractUserId(token), id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 가입", description = "JWT 토큰을 통해 사용자가 특정 그룹에 가입합니다.")
    @PostMapping("{id}/join")
    public ResponseEntity<String> joinGroup(
            @Parameter(description = "JWT 토큰", required = true) @RequestHeader("AuthorizationJWT") String token,
            @Parameter(description = "그룹 ID", required = true) @PathVariable Long id) {
        try {
            groupService.joinGroup(jwtUtil.extractUserId(token), id);
            return ResponseEntity.ok("그룹에 가입되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}