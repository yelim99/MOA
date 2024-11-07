package com.MOA.backend.domain.group.dto.response;

import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.user.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class GroupDetailsResponse {
    private Group group;
    private List<User> users;
    private Map<String, List<String>> images;

    @Builder
    public GroupDetailsResponse(Group group, List<User> users, Map<String, List<String>> images) {
        this.group = group;
        this.users = users.isEmpty() ? new ArrayList<>() : users;
        this.images = images.isEmpty() ? new HashMap<>() : images;
    }
}
