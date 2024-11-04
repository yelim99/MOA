package com.MOA.backend.domain.group.controller;

import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.group.service.GroupService;
import com.MOA.backend.global.auth.jwt.service.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group createdGroup = groupService.create(group);
        return ResponseEntity.ok(createdGroup);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Optional<Group> group = groupService.getGroupById(id);

        return group.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable long id, @RequestBody Group groupDetails) {
        Group updatedGroup = groupService.update(id, groupDetails);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groups")
    public List<Group> getMyGroups(@RequestHeader("Authorizaion") String token){
        String jwt = token.replace("Bearer", "");
        Long userId = jwtUtil.
    }

}
