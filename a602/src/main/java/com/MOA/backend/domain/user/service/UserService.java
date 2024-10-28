package com.MOA.backend.domain.user.service;

import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // UserService.java
    public Optional<User> findByEmail(String email) {
        return userRepository.findByUserEmail(email);
    }


    public void saveUser(User user) {
        userRepository.save(user);
    }
}
