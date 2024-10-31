package com.MOA.backend.domain.user.service;

import com.MOA.backend.domain.user.dto.UserSignupRequestDto;
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
    public Optional<User> findByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }


    public void save(User user) {
        userRepository.save(user);
    }

    public void signup(UserSignupRequestDto userSignupRequestDto) {

        if (userRepository.findByUserEmail(userSignupRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        System.out.println(userSignupRequestDto.getEmail());

        User newUser = new User();
        newUser.setUserEmail(userSignupRequestDto.getEmail());
        newUser.setUserName(userSignupRequestDto.getNickname());
        newUser.setUserImage(userSignupRequestDto.getProfile());

        userRepository.save(newUser);
    }
}
