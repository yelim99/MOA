package com.MOA.backend.domain.user.dto;

import lombok.Data;

@Data
public class DeviceTokenRequest {
    private Long userId;
    private String deviceToken;
}
