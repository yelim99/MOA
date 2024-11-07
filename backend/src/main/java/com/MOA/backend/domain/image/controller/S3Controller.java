package com.MOA.backend.domain.image.controller;

import com.MOA.backend.domain.image.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/moment/{moment_id}/upload")
    ResponseEntity<List<String>> uploadImages(@PathVariable("moment_id") String momentId,
                                       @RequestPart("images") List<MultipartFile> images) {
        return ResponseEntity.ok(s3Service.uploadImages(momentId, images));
    }

    @PostMapping("/user/upload")
    ResponseEntity<String> uploadImages(MultipartFile image) {
        return ResponseEntity.ok(s3Service.uploadImage(image));
    }

//    @DeleteMapping("/moment/{moment_id}/delete")
//    ResponseEntity<String> deleteImage(@PathVariable("moment_id") String momentId, List<String> imageUrls) {
//
//    }

}
