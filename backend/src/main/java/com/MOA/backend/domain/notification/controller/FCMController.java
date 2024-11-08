package com.MOA.backend.domain.notification.controller;

import com.MOA.backend.domain.notification.dto.request.FCMInvitationRequest;
import com.MOA.backend.domain.notification.dto.request.FCMRequest;
import com.MOA.backend.domain.notification.service.FCMService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/alarm")
public class FCMController {

    private final FCMService fcmService;

    @PostMapping("/group")
    public Mono<ResponseEntity<Integer>> pushFcmMessageToGroup(@RequestBody FCMRequest fcmRequest) throws JsonProcessingException {
        log.debug("[+] 푸시 메세지를 전송합니다.");
        return fcmService.sendMessageToGroup(fcmRequest, fcmRequest.getTargetGroup())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .doOnError(e -> log.error("푸시 메세지 전송 중 에러 발생: {}", e.getMessage()))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @PostMapping("/invite")
    public Mono<ResponseEntity<Integer>> sendGroupInvitation(@RequestBody FCMInvitationRequest fcmInvitationRequest) throws JsonProcessingException {
        log.debug("[+] 그룹 초대 메세지를 전송합니다.");
        return fcmService.sendInvitationTo(fcmInvitationRequest)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .doOnError(e -> log.error("그룹 초대 메세지 전송 중 에러 발생: {}", e.getMessage()))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
