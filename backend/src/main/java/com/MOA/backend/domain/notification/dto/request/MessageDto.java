package com.MOA.backend.domain.notification.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDto {
    private Long groupId;
    private Long imageCount;
}
