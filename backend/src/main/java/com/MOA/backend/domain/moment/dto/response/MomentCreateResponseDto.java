package com.MOA.backend.domain.moment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MomentCreateResponseDto {
    private String momentId;
    private String message;
    private String PIN;
}
