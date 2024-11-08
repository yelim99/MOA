package com.MOA.backend.domain.notification.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class FCMSubscriptionRequest {
    private List<Long> groupIds;
    private String token;
}
