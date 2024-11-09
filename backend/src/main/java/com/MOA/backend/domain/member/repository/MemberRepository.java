package com.MOA.backend.domain.member.repository;

import com.MOA.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query()
    List<Member> findByGroupGroupId(Long groupId);

    List<Member> findByUserUserId(Long userId);

    void deleteByGroupGroupIdAndUserUserId(Long groupId, Long userId);

    Boolean existsByUserUserId(Long userId);

    List<Member> findAllByUserUserId(Long userId);

}
