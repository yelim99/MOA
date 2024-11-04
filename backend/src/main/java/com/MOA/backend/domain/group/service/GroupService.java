package com.MOA.backend.domain.group.service;

import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.group.repository.GroupRepository;
import com.MOA.backend.domain.member.entity.Member;
import com.MOA.backend.domain.member.repository.MemberRepository;
import com.MOA.backend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    // 그룹 생성
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    // 그룹 삭제
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    // 그룹 업데이트
    public Group update(Long id, Group newGroup) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 그룹을 찾을 수 없습니다." + id));

        group.setGroupName(newGroup.getGroupName());
        group.setGroupPin(newGroup.getGroupPin());
        group.setGroupDescription(newGroup.getGroupDescription());
        group.setGroupIcon(newGroup.getGroupIcon());
        group.setGroupLightColor(newGroup.getGroupLightColor());
        group.setGroupDarkColor(newGroup.getGroupDarkColor());

        return groupRepository.save(group);
    }

    // 그룹 상세 조회
    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }


    // userId에 해당하는 모든 그룹 가져오기
    public List<User> getGroupUsers(Long groupId) {
        List<Member> memberships = memberRepository.findByGroupId(groupId);
        return memberships.stream()
                .map(Member::getUser)
                .collect(Collectors.toList());
    }

    public void leaveGroup(Long userId, Long groupId) {
        memberRepository.deleteByGroupIdAndUserId(userId, groupId);
    }


}
