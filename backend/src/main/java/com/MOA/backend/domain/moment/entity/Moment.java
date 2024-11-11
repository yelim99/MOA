package com.MOA.backend.domain.moment.entity;

import com.MOA.backend.domain.moment.dto.request.MomentUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "moment")
@Getter
@NoArgsConstructor
public class Moment {

    @Id // MongoDB에서 자동으로 생성하는 ID
    private ObjectId id;

    private Long groupId;
    private String momentPin;
    private List<Long> userIds;
    private String momentName;
    private String momentDescription;
    private String momentOwner;
    private String momentOwnerEmail;
    private String uploadOption;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @Builder
    public Moment(Long groupId, String momentPin, List<Long> userIds, String momentName, String momentDescription,
                  String momentOwner, String momentOwnerEmail, String uploadOption) {
        this.groupId = groupId;
        this.momentPin = momentPin;
        this.userIds = userIds == null ? new ArrayList<>() : userIds;
        this.momentName = momentName;
        this.momentDescription = momentDescription;
        this.momentOwner = momentOwner;
        this.uploadOption = uploadOption;
        this.momentOwnerEmail = momentOwnerEmail;
    }

    public void update(MomentUpdateRequestDto momentUpdateRequestDto) {
        this.momentName = momentUpdateRequestDto.getMomentName();
        this.momentDescription = momentUpdateRequestDto.getMomentDescription();
    }

    public void update(List<Long> userIds) {
        this.userIds = userIds;
    }
}
