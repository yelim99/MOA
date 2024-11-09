package com.MOA.backend.domain.image.controller;

import com.MOA.backend.domain.image.service.S3Service;
import com.MOA.backend.domain.moment.service.MomentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;
    private final MomentService momentService;

    @PostMapping("/moment/{moment_id}/upload")
    ResponseEntity<List<String>> uploadImages(
            @PathVariable("moment_id") String momentId,
            @RequestPart("images") List<MultipartFile> images
    ) {
        return ResponseEntity.ok(s3Service.uploadImages(momentId, images));
    }

    @PostMapping("/group/{groupId}/upload")
    ResponseEntity<?> uploadImagesInGroup(
            @RequestHeader("Authorization") String token,
            @PathVariable("groupId") Long groupId,
            @RequestPart List<MultipartFile> images
    ) {
        List<String> urls =
                s3Service.uploadImages(momentService.createMomentForGroup(token, groupId, images), images);

        return ResponseEntity.ok(urls);
    }

    @PostMapping("/user/upload")
    ResponseEntity<Map<String, String>> uploadAIImage(
            @RequestHeader("Authorization") String token,
            @RequestPart("image") MultipartFile image
    ) {
        return ResponseEntity.ok(Map.of("url", s3Service.uploadUserProfile(token, image)));
    }

    @DeleteMapping("/moment/{moment_id}/delete")
    ResponseEntity<List<String>> deleteImage(
            @RequestBody List<String> imageUrls
    ) {
        return ResponseEntity.ok(s3Service.deleteImages(imageUrls));
    }

    @PostMapping("/user/img")
    ResponseEntity<Map<String, String>> uploadUserImage(
            @RequestHeader("Authorization") String token,
            @RequestPart("image") MultipartFile image
    ) {
        return ResponseEntity.ok(Map.of("url", s3Service.uploadUserImg(token, image)));
    }

}