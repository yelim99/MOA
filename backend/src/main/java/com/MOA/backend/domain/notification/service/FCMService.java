package com.MOA.backend.domain.notification.service;

import com.MOA.backend.domain.notification.dto.request.FCMMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMService {

    @Value("${spring.fcm.api.url}")
    private String FCM_API_URL;

    private final WebClient webClient;

    public Mono<Integer> sendMessageToGroup(String userName, Long groupId) throws JsonProcessingException {
        String message = makeGroupMessage(userName, groupId);
        log.info("+++++++{}", message);
        String accessToken = getAccessToken();
        log.info("Access Token: {}", accessToken);

        return webClient.post()
                .uri(FCM_API_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .bodyValue(message)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful() ? 1 : 0)
                .doOnError(e -> {
                    log.error("[-] FCM 전송 오류 :: " + e.getMessage());
                    log.error("[토픽: " + groupId + "] 메세지 전송 실패");
                })
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
                    .fromStream(new ClassPathResource("firebase-adminsdk.json").getInputStream())
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
     * @param userName
     * @param groupId
     * @return JSON 형식의 메세지 문자열
     * @throws JsonProcessingException
     */
    private String makeGroupMessage(String userName, Long groupId) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        FCMMessage fcmMessage = FCMMessage
                .builder()
                .message(FCMMessage.Message.builder()
                        .topic(groupId.toString())
                        .notification(FCMMessage.Notification.builder()
                                .title("새로운 사진이 업로드되었습니다") // 고정된 제목
                                .body(userName + "님이 그룹에 새로운 사진을 추가했습니다. 확인해보세요!") // 고정된 본문
                                .build())
                        .build())
                .validateOnly(false)
                .build();
        return om.writeValueAsString(fcmMessage);
    }

    public void subscribeToGroups(String token, Long groupId) {
        String topic = groupId.toString();
        try {
            // 단일 token을 topic에 구독
            FirebaseMessaging.getInstance().subscribeToTopic(Collections.singletonList(token), topic);
            log.info("토큰 '{}'이 '{}' 그룹에 성공적으로 구독되었습니다.", token, topic);
        } catch (FirebaseMessagingException e) {
            log.error("토큰 '{}'을 '{}' 그룹에 구독하는 중 오류 발생: {}", token, topic, e.getMessage());
            throw new RuntimeException("구독 실패: " + e.getMessage(), e); // 필요시 예외 재던짐
        }
    }

    public void unsubscribeFromGroups(String token, Long groupId) {
        String topic = groupId.toString();
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(Collections.singletonList(token), topic);
            log.info("토큰 '{}'이 '{}' 그룹에서 성공적으로 구독 취소되었습니다.", token, topic);
        } catch (FirebaseMessagingException e) {
            log.error("토큰 '{}'을 '{}' 그룹에서 구독 취소하는 중 오류 발생: {}", token, topic, e.getMessage());

            throw new RuntimeException("구독 취소 실패: " + e.getMessage(), e);
        }
    }
}
