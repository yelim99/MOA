package com.MOA.backend.domain.user.service;

import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.member.entity.Member;
import com.MOA.backend.domain.member.repository.MemberRepository;
import com.MOA.backend.domain.user.dto.UserSignupRequestDto;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;


    public Optional<User> findByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

    public Optional<User> finfByUserId(long id) {
        return userRepository.findById(id);
    }

    public User signup(UserSignupRequestDto userSignupRequestDto) {

        if (userRepository.findByUserEmail(userSignupRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        System.out.println(userSignupRequestDto.getEmail());

        User newUser = new User();
        newUser.setUserEmail(userSignupRequestDto.getEmail());
        newUser.setUserName(userSignupRequestDto.getNickname());
        newUser.setUserImage(userSignupRequestDto.getProfile());

        return userRepository.save(newUser);
    }

    // 유저 업데이트
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 유저를 찾을 수 없습니다" + id));

        user.setUserName(userDetails.getUserName());
        user.setUserImage(userDetails.getUserImage());

        return userRepository.save(user);
    }

    public List<Group> getUserGroups(Long userId) {

        List<Member> memberships = memberRepository.findByUserId(userId);

        return memberships.stream()
                .map(Member::getGroup)
                .collect(Collectors.toList());
    }

}
