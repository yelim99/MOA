package com.MOA.backend.domain.group.controller;

import com.MOA.backend.domain.group.dto.response.GroupDetailsResponse;
import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.group.service.GroupService;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.service.UserService;
import com.MOA.backend.global.auth.jwt.service.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    // 그룹 생성하기
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestHeader("Authorization") String token, @RequestBody Group group) {
        Group createdGroup = groupService.create(jwtUtil.extractUserId(jwtUtil.remove(token)), group);
        return ResponseEntity.ok(createdGroup);
    }

    // 그룹 상세 조회하기
    @GetMapping("/{id}/details")
    public ResponseEntity<GroupDetailsResponse> getGroupDetails(@PathVariable Long id) {
        Optional<Group> group = groupService.getGroupById(id);
        List<User> users = groupService.getGroupUsers(id);
        return group.map(value -> ResponseEntity.ok(new GroupDetailsResponse(value, users)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 그룹 수정하기
    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable long id, @RequestBody Group groupDetails) {
        Group updatedGroup = groupService.update(id, groupDetails);
        return ResponseEntity.ok(updatedGroup);
    }

    // 그룹 삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 그룹 나가기
    @DeleteMapping("/{id}/leave")
    public ResponseEntity<Void> leaveGroup(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        groupService.leaveGroup(jwtUtil.extractUserId(jwtUtil.remove(token)), id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}/join")
    public ResponseEntity<String> joinGroup(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            groupService.joinGroup(jwtUtil.extractUserId(jwtUtil.remove(token)), id);
            return ResponseEntity.ok("그룹에 가입되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
