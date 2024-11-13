package com.MOA.backend.domain.user.service;

import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.image.dto.FaceEmbeddingDTO;
import com.MOA.backend.domain.image.service.S3Service;
import com.MOA.backend.domain.member.entity.Member;
import com.MOA.backend.domain.member.repository.MemberRepository;
import com.MOA.backend.domain.user.dto.UserSignupRequestDto;
import com.MOA.backend.domain.user.dto.UserUpdateRequestDto;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.repository.UserRepository;
import com.MOA.backend.global.auth.jwt.service.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Optional<User> findByUserId(Long id) {
        return userRepository.findById(id);
    }

    public User signup(UserSignupRequestDto userSignupRequestDto) {

        if (userRepository.findByUserEmail(userSignupRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        System.out.println(userSignupRequestDto.getEmail());

        User newUser = User.builder()
                .userEmail(userSignupRequestDto.getEmail())
                .userName(userSignupRequestDto.getNickname())
                .userImage(userSignupRequestDto.getProfile())
                .build();

        return userRepository.save(newUser);
    }

    @Transactional
    public User updateUser(Long userId, String nickname, String imageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 유저를 찾을 수 없습니다.: " + userId));

        user.update(nickname, imageUrl);
        userRepository.save(user);

        return user;
    }

    @Transactional
    public User updateUser(Long userId, String nickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 유저를 찾을 수 없습니다.: " + userId));

        user.update(nickname);
        userRepository.save(user);

        return user;
    }

    public List<Group> getUserGroups(Long userId) {

        List<Member> memberships = memberRepository.findByUserUserId(userId);

        return memberships.stream()
                .map(Member::getGroup)
                .collect(Collectors.toList());
    }


    // 등록한 얼굴의 임베딩 값 저장
    public void updateFaceEmbedding(FaceEmbeddingDTO faceEmbeddingDTO) {
        User user = userRepository.findById(faceEmbeddingDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 유저를 찾을 수 없습니다"));

        user.setFaceEmbedding(faceEmbeddingDTO.getFaceEmbedding());
        userRepository.save(user);  // 엔티티 업데이트
    }

//    // 등록된 임베딩 값 가져오기
//    public byte[] getEmbedding(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 유저를 찾을 수 없습니다" + userId));
//
//        return user.getFaceEmbedding();
//    }


    public void updateDeviceToken(Long userId, String deviceToken) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setDeviceToken(deviceToken);
            userRepository.save(user);
        });

    }

    public List<User> getUsers(List<Long> userIds) {
        return userRepository.findAllByUserIdIn(userIds);
    }

}
