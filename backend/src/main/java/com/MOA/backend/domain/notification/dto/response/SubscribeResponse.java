package com.MOA.backend.domain.notification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscribeResponse {
    private int successCount;
    private int failureCount;
}
