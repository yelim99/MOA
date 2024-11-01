package com.MOA.backend.domain.moment.entity;

import com.MOA.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "moment")
@Getter
@NoArgsConstructor
public class Moment extends BaseEntity {

    @Id // MongoDB에서 자동으로 생성하는 ID
    @Field(name = "momentId")
    private ObjectId id;

    private Long groupId;
    private String momentPin;
    private List<Long> userIds;
    private String momentName;
    private String momentDescription;
    private String momentOwner;

    @Enumerated(EnumType.STRING)
    private UploadOption uploadOption;

    @Builder
    public Moment(Long groupId, String momentPin, List<Long> userIds, String momentName, String momentDescription,
                  String momentOwner, UploadOption uploadOption) {
        this.groupId = groupId;
        this.momentPin = momentPin;
        this.userIds = userIds == null ? new ArrayList<>() : userIds;
        this.momentName = momentName;
        this.momentDescription = momentDescription;
        this.momentOwner = momentOwner;
        this.uploadOption = uploadOption;
    }
}
