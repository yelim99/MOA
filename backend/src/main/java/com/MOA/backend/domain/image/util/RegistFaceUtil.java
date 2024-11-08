package com.MOA.backend.domain.image.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RegistFaceUtil {

    private final WebClient webClient;

    @Value("${fast.api.base-url}")
    private String fastBaseUrl;

    public RegistFaceUtil(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(fastBaseUrl).build();
    }

    //.FastAPI에 등록한 얼굴의 S3 URL을 보내고, BLOB 데이터(바이너리 데이터)를 받아오기
    public byte[] GetFaceEmbeddingFromFast(String imgUrl) {
        return this.webClient.post()
                // fast로 보내는 url
                .uri("/fast/regist_face")
                // json으로 body에 담아서 url 전송
                .bodyValue(new ImageRequest(imgUrl))
                // fastAPI 응답 가져오는 메소드
                .retrieve()
                // 응답 데이터를 바이너리 배열(byte[])로 변환하고,
                // block() 메서드를 사용하여 비동기 응답을 동기 방식으로 처리해 최종적으로 바이너리 데이터를 반환
                .bodyToMono(byte[].class)
                .block();  // 동기 방식으로 처리

    }

    // 이미지 URL을 FastAPI로 보내기 위한 DTO
    public static class ImageRequest {
        @JsonProperty("imgUrl")
        private String imgUrl;

        public ImageRequest(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

}
