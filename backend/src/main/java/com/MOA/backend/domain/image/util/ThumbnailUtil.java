package com.MOA.backend.domain.image.util;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
public class ThumbnailUtil {

    public static InputStream getThumbnail(MultipartFile multipartFile, int width, int height) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(multipartFile.getInputStream())
                    .size(width, height)
                    .outputFormat("jpg")
                    .toOutputStream(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("썸네일 이미지 생성 실패 : {}", e);
        }
    }
}
