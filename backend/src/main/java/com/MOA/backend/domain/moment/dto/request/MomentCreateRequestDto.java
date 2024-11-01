package com.MOA.backend.domain.moment.dto.request;

import com.MOA.backend.domain.moment.entity.UploadOption;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MomentCreateRequestDto {
    private Long groupId;
    private String momentName;
    private String momentDescription;

    @Enumerated(EnumType.STRING)
    private UploadOption uploadOption;
}
