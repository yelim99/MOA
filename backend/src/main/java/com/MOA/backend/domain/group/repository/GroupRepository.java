package com.MOA.backend.domain.group.repository;

import com.MOA.backend.domain.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupId(Long groupId);
}
