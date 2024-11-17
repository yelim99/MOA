package com.MOA.backend.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FCMConfig {

    @Value("${spring.fcm.key.path}")
    private String SERVICE_ACCOUNT_JSON;

    @PostConstruct
    public void init() {
        try (InputStream serviceAccount = getServiceAccountStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            log.info("파이어베이스 서버와의 연결에 성공했습니다.");
        } catch (IOException e) {
            log.error("파이어베이스 서버와의 연결에 실패했습니다. 인증 파일 경로: {}", SERVICE_ACCOUNT_JSON, e);
        }
    }

    private InputStream getServiceAccountStream() throws IOException {
        String serviceAccountPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

        if (serviceAccountPath != null && !serviceAccountPath.isEmpty()) {
            log.info("환경 변수로 지정된 Firebase 인증 파일 경로: {}", serviceAccountPath);
            return new FileInputStream(serviceAccountPath);
        } else {
            log.info("클래스패스에서 Firebase 인증 파일을 로드합니다.");
            InputStream stream = new ClassPathResource("firebase-adminsdk.json").getInputStream();
            if (stream == null) {
                throw new IOException("클래스패스에서 firebase-adminsdk.json 파일을 찾을 수 없습니다.");
            }
            return stream;
        }
    }

}
