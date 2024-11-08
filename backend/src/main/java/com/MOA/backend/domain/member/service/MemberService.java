package com.MOA.backend.domain.member.service;

import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.member.dto.response.MemberResponseDto;
import com.MOA.backend.domain.member.entity.Member;
import com.MOA.backend.domain.member.repository.MemberRepository;
import com.MOA.backend.global.auth.jwt.service.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public MemberResponseDto modifyMemberNickname(Long userId, String nickname) {
        Member member = memberRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("해당 멤버가 존재하지 않습니다."));

        member.update(nickname);
        memberRepository.save(member);
        return MemberResponseDto.builder().nickname(nickname).build();
    }

    public List<Long> findAllGroupIdByUserId(String token) {
        Long userId = jwtUtil.extractUserId(token);
        return memberRepository.findAllByUserUserId(userId)
                .stream().map(member -> member.getGroup().getGroupId()).toList();
    }
}
