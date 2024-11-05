package com.MOA.backend.domain.group.service;

import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.group.repository.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

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


}
