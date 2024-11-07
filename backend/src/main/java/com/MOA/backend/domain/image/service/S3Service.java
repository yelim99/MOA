package com.MOA.backend.domain.image.service;

import com.MOA.backend.domain.moment.entity.Moment;
import com.MOA.backend.domain.moment.service.MomentService;
import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.service.UserService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private static final List<String> ALLOWED_EXTENSIONS = List.of(
            ".jpg", ".jpeg", ".png", ".webp", ".svg", ".heic", ".bmp", ".tiff", ".tif"
    );

    private final UserService userService;
    private final MomentService momentService;
    private final AmazonS3 amazonS3;

    @Value("${cloud.s3.bucket}")
    private String bucket;

    public List<String> uploadImages(String momentId, List<MultipartFile> images) {
        /* 해당 Moment 먼저 찾기
            - Moment에 GroupId랑 MomentId가 다 들어가 있음
         */
        Moment moment = momentService.getMomentEntity(momentId);
        Long groupId = moment.getGroupId();

        List<String> imageUrls = new ArrayList<>();
        images.forEach(image -> {
            if(image.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "빈 파일은 업로드할 수 없습니다.");
            }

           String imageName = createImageName(groupId, momentId, image.getOriginalFilename());
           ObjectMetadata objectMetadata = new ObjectMetadata();
           objectMetadata.setContentLength(image.getSize());
           objectMetadata.setContentType(image.getContentType());

           try (InputStream inputStream = image.getInputStream()) {
               amazonS3.putObject(new PutObjectRequest(bucket, imageName, inputStream, objectMetadata)
               .withCannedAcl(CannedAccessControlList.PublicRead));

               String imageUrl = amazonS3.getUrl(bucket, imageName).toString();
               imageUrls.add(imageUrl);
           } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패하였습니다.");
           }
        });

        return imageUrls;
    }

    // https://moa-s3-bucket.s3.amazonaws.com/group/{groupId}/moment/{momentId}/{imageName}
    public String createImageName(Long groupId, String momentId, String imageName) {
        return "group/" + groupId + "/moment/" + momentId + "/"
                + UUID.randomUUID().toString().concat(getFileExtension(imageName));
    }

    private String getFileExtension(String imageName) {
        try {
            String extension = imageName.substring(imageName.lastIndexOf(".")).toLowerCase();

            if(!ALLOWED_EXTENSIONS.contains(extension)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "허용되지 않는 파일 확장자입니다.");
            }

            return extension;
        } catch(StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일입니다.");
        }
    }

    // https://moa-s3-bucket.s3.amazonaws.com/user/{userEmail}/profile.{확장자}
    public String uploadImage(MultipartFile image) {
        // TODO: 리팩토링 필요: 로그인 유저의 정보 가져오기
        User loginUser = userService.findByUserEmail("moa@moa.com").orElseThrow(NoSuchElementException::new);
        log.info("loginUser: {}, {}", loginUser.getUserId(), loginUser.getUserEmail());

        if(image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "빈 파일은 업로드할 수 없습니다.");
        }

        //
        String imageName = "/user/" + loginUser.getUserEmail() + "/" +
        "profile".concat(getFileExtension(Objects.requireNonNull(image.getOriginalFilename())));
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(image.getSize());
        objectMetadata.setContentType(image.getContentType());

        try (InputStream inputStream = image.getInputStream()) {
            if(amazonS3.doesObjectExist(bucket, imageName)) {
                log.info("이미 존재하는 파일을 덮어씁니다. {}", imageName);
            }
            amazonS3.putObject(new PutObjectRequest(bucket, imageName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return amazonS3.getUrl(bucket, imageName).toString();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패하였습니다.");
        }
    }

    public List<String> deleteImages(List<String> imageUrls) {
        List<String> removedImages = new ArrayList<>();
        imageUrls.forEach(imageUrl -> {
            try {
                if(amazonS3.doesObjectExist(bucket, imageUrl)) {
                    amazonS3.deleteObject(new DeleteObjectRequest(bucket, imageUrl));
                    removedImages.add(imageUrl);
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "이미지를 찾을 수 없습니다.");
                }
            } catch (AmazonS3Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지를 삭제하는 중 오류가 발생했습니다.", e);
            }
        });

        return removedImages;
    }

    // S3에 저장된 회원 경로 찾아오기
    public String getUserProfile(String userEmail) {
        String prefix = "user/" + userEmail + "/";
        try {
            ListObjectsV2Request request = new ListObjectsV2Request()
                    .withBucketName(bucket)
                    .withPrefix(prefix)
                    .withMaxKeys(1);

            ListObjectsV2Result response = amazonS3.listObjectsV2(request);

            if(!response.getObjectSummaries().isEmpty()) {
                String imageUrl = response.getObjectSummaries().get(0).getKey();
                return amazonS3.getUrl(bucket, imageUrl).toString();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "이미지를 찾을 수 없습니다.");
            }
        } catch(AmazonS3Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지를 가져오는 중 오류가 발생했습니다.", e);
        }
    }

}
