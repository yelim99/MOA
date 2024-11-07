package com.MOA.backend.global.auth.refresh.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "token", timeToLive = 1209600) //14일
@AllArgsConstructor
@Getter
public class Token {
    @Id
    private Long id;
    private String refreshToken;
}
