package com.MOA.backend.domain.member.repository;

import com.MOA.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByGroupId(Long groupId);

    List<Member> findByUserId(Long userId);

    void deleteByGroupIdAndUserId(Long groupId, Long UserId);

}
