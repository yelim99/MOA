package com.MOA.backend.domain.notification.dto.request;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FCMInvitationRequest {
    private String userToken;
    private String inviteName;
    private String groupName;
}
