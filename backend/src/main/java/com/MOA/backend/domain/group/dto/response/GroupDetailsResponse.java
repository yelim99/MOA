package com.MOA.backend.domain.group.dto.response;

import com.MOA.backend.domain.group.entity.Group;
import com.MOA.backend.domain.user.entity.User;
import lombok.*;

import java.util.*;

@Getter
public class GroupDetailsResponse {
    private Group group;
    private List<User> users;
    private Map<String, Map<String, List<String>>> images;
    private Map<String, Date> expiredAt;

    @Builder
    public GroupDetailsResponse(Group group, List<User> users,
                                Map<String, Map<String, List<String>>> images, Map<String, Date> expiredAt) {
        this.group = group;
        this.users = users.isEmpty() ? new ArrayList<>() : users;
        this.images = images.isEmpty() ? new HashMap<>() : images;
        this.expiredAt = expiredAt.isEmpty() ? new HashMap<>() : expiredAt;
    }
}
