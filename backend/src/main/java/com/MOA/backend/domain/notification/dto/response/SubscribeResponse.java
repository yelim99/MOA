package com.MOA.backend.domain.notification.dto.response;

import lombok.Data;

@Data
public class SubscribeResponse {
    private boolean success;
    private String message;
}
