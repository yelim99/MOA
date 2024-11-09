package com.MOA.backend.domain.group.service;

import com.MOA.backend.domain.group.dto.request.GroupCreateDto;
import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.group.repository.GroupRepository;
import com.MOA.backend.domain.member.entity.Member;
import com.MOA.backend.domain.member.repository.MemberRepository;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    // 그룹 생성
    // 그룹 생성
    public Group create(Long userId, GroupCreateDto groupDto) {
        // Group 엔티티를 생성하고 DTO의 값들을 설정합니다.
        Group group = new Group();
        group.setGroupName(groupDto.getGroupName());
        group.setGroupPin(groupDto.getPIN());
        group.setGroupDescription(groupDto.getGroupDescription());
        group.setGroupColor(groupDto.getColor());
        group.setGroupIcon(groupDto.getIcon());

        // 그룹을 저장하고 사용자 추가 로직을 수행합니다.
        Group savedGroup = groupRepository.save(group);
        addUserToGroup(userId, savedGroup);

        return savedGroup;
    }


    // 그룹 삭제
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    // 그룹 업데이트
    public Group update(Long id, GroupCreateDto groupDto) {
        Group existingGroup = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 그룹을 찾을 수 없습니다." + id));

        if (existingGroup.getGroupName() != null) existingGroup.setGroupName(groupDto.getGroupName());
        if (existingGroup.getGroupPin() != null) existingGroup.setGroupPin(groupDto.getPIN());
        if (existingGroup.getGroupDescription() != null)
            existingGroup.setGroupDescription(groupDto.getGroupDescription());
        if (existingGroup.getGroupColor() != null) existingGroup.setGroupColor(groupDto.getColor());
        if (existingGroup.getGroupIcon() != null) existingGroup.setGroupIcon(groupDto.getIcon());

        return groupRepository.save(existingGroup);
    }

    // 그룹 상세 조회
    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }


    // userId에 해당하는 모든 그룹 가져오기
    public List<User> getGroupUsers(Long groupId) {
        List<Member> memberships = memberRepository.findByGroupGroupId(groupId);
        return memberships.stream()
                .map(Member::getUser)
                .collect(Collectors.toList());
    }

    public boolean isUserInGroup(Long userId) {
        return memberRepository.existsByUserUserId(userId);
    }

    public void joinGroup(Long userId, Long groupId) {
        if (!isUserInGroup(userId)) {
            Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 그룹이 없습니다" + groupId));
            addUserToGroup(userId, group);
        } else {
            throw new IllegalArgumentException("이미 그룹에 속해 있습니다.");
        }

    }

    public void leaveGroup(Long userId, Long groupId) {
        memberRepository.deleteByGroupGroupIdAndUserUserId(userId, groupId);
    }

    private void addUserToGroup(Long userId, Group group) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다." + userId));

        Member member = new Member();
        member.setUser(user);
        member.setGroup(group);
        member.setJoinDate(LocalDateTime.now());

        memberRepository.save(member);
    }


}