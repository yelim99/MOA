package com.MOA.backend.domain.notification.service;

import com.MOA.backend.domain.notification.dto.FCMInvitationRequest;
import com.MOA.backend.domain.notification.dto.FCMMessage;
import com.MOA.backend.domain.notification.dto.FCMRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class FCMService {

    @Value("${spring.fcm.key.path}")
    private String SERVICE_ACCOUNT_JSON;
    @Value("${spring.fcm.api.url}")
    private String FCM_API_URL;

    private final WebClient webClient;

    /**
     * @param fcmDto
     * @param topic
     * @throws JsonProcessingException
     */
    public Mono<Integer> sendMessageToGroup(FCMRequest fcmDto, String topic) throws JsonProcessingException {
        String message = makeGroupMessage(fcmDto, topic);
        log.info("+++++++{}", message);
        String accessToken = getAccessToken();

        return webClient.post()
                .uri(FCM_API_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .bodyValue(message)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful() ? 1 : 0)
                .doOnError(e -> {
                    log.error("[-] FCM 전송 오류 :: " + e.getMessage());
                    log.error("[토픽: " + topic + "] 메세지 전송 실패");
                })
                .onErrorReturn(0);
    }

    /**
     *
     *
     * @param fcmInvitationRequest
     * @return
     * @throws JsonProcessingException
     */
    public Mono<Integer> sendInvitationTo(FCMInvitationRequest fcmInvitationRequest) throws JsonProcessingException {
        String message = makeInvitationMessage(fcmInvitationRequest);
        log.info("Invitation Message: {}", message);
        String accessToken = getAccessToken();

        return webClient.post()
                .uri(FCM_API_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .bodyValue(message)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful() ? 1 : 0)
                .doOnError(e -> log.error("[-] FCM 초대 알림 전송 오류 :: " + e.getMessage()))
                .onErrorReturn(0);
    }


    /**
     * fcm에서는 토큰을 통해 타겟에 메세지를 전송하는 방식.
     * firebase에서 제공하는 sdk를 통해 만들어지는 accesstoken
     *
     * @return 토큰
     */
    private String getAccessToken() {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(SERVICE_ACCOUNT_JSON).getInputStream())
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
            googleCredentials.refreshIfExpired();
            log.info("getAccessToken() - googleCredentials: {} ", googleCredentials.getAccessToken().getTokenValue());

            return googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패 했습니다.");
        }
    }

    /**
     * 입력받는 dto를 통해 topic을 구독하는
     * 사람들에게 보내는 메세지를 만듭니다.
     *
     * @param fcmDto
     * @param topic  그룹
     * @return JSON 형식의 메세지 문자열
     * @throws JsonProcessingException
     */
    private String makeGroupMessage(FCMRequest fcmDto, String topic) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        FCMMessage fcmMessage = FCMMessage
                .builder()
                .message(FCMMessage.Message.builder()
                        .topic(topic)
                        .notification(FCMMessage.Notification.builder()
                                .title(fcmDto.getTitle())
                                .body(fcmDto.getBody())
                                .build())
                        .build())
                .validateOnly(false)
                .build();
        return om.writeValueAsString(fcmMessage);
    }

    /**
     * 초대를 위한 메세지를 만듭니다.
     *
     * @param fcmInvitationRequest
     * @return
     * @throws JsonProcessingException
     */
    private String makeInvitationMessage(FCMInvitationRequest fcmInvitationRequest) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        FCMMessage fcmMessage = FCMMessage.builder()
                .message(FCMMessage.Message.builder()
                        .token(fcmInvitationRequest.getUserToken())
                        .notification(FCMMessage.Notification.builder()
                                .title("그룹 초대")
                                .body(fcmInvitationRequest.getInviteName() + "님이 " + fcmInvitationRequest.getGroupName() + "그룹에 초대했습니다.")
                                .build())
                        .build())
                .validateOnly(false)
                .build();
        return om.writeValueAsString(fcmMessage);
    }

}
