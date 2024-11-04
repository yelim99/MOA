package com.MOA.backend.domain.group.dto.response;

import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GroupDetailsResponse {
    private Group group;
    private List<User> users;
}
