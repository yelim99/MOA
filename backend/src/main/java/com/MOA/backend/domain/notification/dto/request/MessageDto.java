package com.MOA.backend.domain.notification.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDto {
    private Long groupId;
    private Long imageCount;

    @JsonCreator
    public MessageDto(
            @JsonProperty("groupId") Long groupId,
            @JsonProperty("imageCount") Long imageCount
    ) {
        this.groupId = groupId;
        this.imageCount = imageCount;
    }
}

