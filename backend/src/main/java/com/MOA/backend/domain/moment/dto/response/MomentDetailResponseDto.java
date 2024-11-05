package com.MOA.backend.domain.moment.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.List;

@Builder
@Getter
public class MomentDetailResponseDto {
    private String id;
    private Long groupId;
    private String momentPin;
    private List<Long> userIds;
    private String momentName;
    private String momentDescription;
    private String momentOwner;
    private String uploadOption;
}
