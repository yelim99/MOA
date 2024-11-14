package com.MOA.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);    // 최소 스레드 수를 늘려 동시 작업 개수 증가
        executor.setMaxPoolSize(100);     // 최대 스레드 수를 확장하여 고성능 모드
        executor.setQueueCapacity(500);  // 큐 용량을 늘려 요청 대기 공간 확장
        executor.setThreadNamePrefix("S3Uploader-");
        executor.setKeepAliveSeconds(120); // 스레드 유지 시간 설정
        executor.initialize();
        return executor;
    }
}
