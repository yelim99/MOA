package com.MOA.backend.domain.member.service;

import com.MOA.backend.domain.member.dto.response.MemberResponseDto;
import com.MOA.backend.domain.member.entity.Member;
import com.MOA.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResponseDto modifyMemberNickname(Long userId, String nickname) {
        Member member = memberRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("해당 멤버가 존재하지 않습니다."));

        member.update(nickname);
        memberRepository.save(member);
        return MemberResponseDto.builder().nickname(nickname).build();
    }
}
