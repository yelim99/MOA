package com.MOA.backend.domain.user.controller;

import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);

        return ResponseEntity.ok(updatedUser);
    }


}
