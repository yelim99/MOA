package com.MOA.backend.domain.image.service;

import com.MOA.backend.domain.image.dto.FaceEmbeddingDTO;
import com.MOA.backend.domain.image.util.RegistFaceUtil;
import com.MOA.backend.domain.image.util.ThumbnailUtil;
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
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private static final List<String> ALLOWED_EXTENSIONS = List.of(
            ".jpg", ".jpeg", ".png", ".webp", ".svg", ".heic", ".bmp", ".tiff", ".tif", ".jfif"
    );

    private final UserService userService;
    private final MomentService momentService;
    private final AmazonS3 amazonS3;
    private final RegistFaceUtil faceUtil;

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

           String imageName = createImageName(image.getOriginalFilename());
           String originalPath = createOriginalImagePath(groupId, momentId);
           String thumbnailPath = createThumbnailImagePath(groupId, momentId);

           // 원본 사진 업로드
           uploadOriginalImage(image, originalPath + imageName);

           // 썸네일 사진 업로드
            uploadThumbnailImage(image, thumbnailPath + imageName);
            imageUrls.add(amazonS3.getUrl(bucket, thumbnailPath + imageName).toString());
        });

        return imageUrls;
    }

    // https://moa-s3-bucket.s3.amazonaws.com/group/{groupId}/moment/{momentId}/{imageName}
    public String createOriginalImagePath(Long groupId, String momentId) {
        return "group/" + groupId + "/moment/" + momentId + "/original/";
    }

    public String createThumbnailImagePath(Long groupId, String momentId) {
        return "group/" + groupId + "/moment/" + momentId + "/thumbnail/";
    }

    public String createImageName(String imageName) {
        return UUID.randomUUID().toString().concat(getFileExtension(imageName));
    }

    private void uploadOriginalImage(MultipartFile image, String imagePath) {
        try (InputStream inputStream = image.getInputStream()) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(image.getSize());
            objectMetadata.setContentType(image.getContentType());

            amazonS3.putObject(new PutObjectRequest(bucket, imagePath, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException("원본 사진 업로드에 실패하였습니다. {}", e);
        }
    }

    private void uploadThumbnailImage(MultipartFile thumbnailImage, String imagePath) {
        try (InputStream inputStream = ThumbnailUtil.getThumbnail(thumbnailImage, 200, 200)) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setContentType("image/jpeg");

            amazonS3.putObject(new PutObjectRequest(bucket, imagePath, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException("썸네일 사진 업로드에 실패하였습니다. {}", e);
        }
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

    // 유저 사진 업로드
    // https://moa-s3-bucket.s3.amazonaws.com/user/{userEmail}/profile.{확장자}
    public String uploadUserProfile(MultipartFile image) {
        // TODO: 리팩토링 필요: 로그인 유저의 정보 가져오기
        User loginUser = userService.findByUserEmail("moa@moa.com").orElseThrow(NoSuchElementException::new);
        log.info("loginUser: {}, {}", loginUser.getUserId(), loginUser.getUserEmail());

        if(image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "빈 파일은 업로드할 수 없습니다.");
        }

        String imageName = "/user/" + loginUser.getUserEmail()
                + "/profile".concat(getFileExtension(Objects.requireNonNull(image.getOriginalFilename())));

        uploadOriginalImage(image, imageName);
        String fileUrl = amazonS3.getUrl(bucket, imageName).toString();

        // fast에서 임베딩 값 받아오기
        byte[] faceEmbedding = faceUtil.GetFaceEmbeddingFromFast(fileUrl);

        // FaceEmbeddingDTO에 값 설정
        FaceEmbeddingDTO faceEmbeddingDTO = FaceEmbeddingDTO.builder()
                .userId(loginUser.getUserId())
                .faceEmbedding(faceEmbedding)
                .build();

        userService.updateFaceEmbedding(faceEmbeddingDTO);



        return fileUrl;
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

    // Group 안의 모든 사진 URL 경로 조회
    public Map<String, Map<String, List<String>>> getImagesInGroup(Long groupId, List<String> momentIds) {
        Map<String, Map<String, List<String>>> imagesByMoment = new HashMap<>();
        imagesByMoment.put("orgImgs", new HashMap<>());
        imagesByMoment.put("thumbImgs", new HashMap<>());
        for(String momentId : momentIds) {
            List<String> orgImgs = new ArrayList<>();
            List<String> thumbImgs = new ArrayList<>();
            try {
                ListObjectsV2Request orgRequest = new ListObjectsV2Request()
                        .withBucketName(bucket)
                        .withPrefix("group/" + groupId + "/moment/" + momentId + "/original");
                ListObjectsV2Request thumbRequest = new ListObjectsV2Request()
                        .withBucketName(bucket)
                        .withPrefix("group/" + groupId + "/moment/" + momentId + "/thumbnail");

                ListObjectsV2Result orgResult = amazonS3.listObjectsV2(orgRequest);
                ListObjectsV2Result thumbResult = amazonS3.listObjectsV2(thumbRequest);

                for(int i = 0; i < orgResult.getObjectSummaries().size(); i++) {
                    orgImgs.add(orgResult.getObjectSummaries().get(i).getKey());
                    thumbImgs.add(thumbResult.getObjectSummaries().get(i).getKey());
                }

                log.info("imageSources: {}", thumbImgs);

                imagesByMoment.get("orgImgs").put(momentId, orgImgs);
                imagesByMoment.get("thumbImgs").put(momentId, thumbImgs);

            } catch (AmazonS3Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "이미지 목록을 조회하는 중 오류가 발생했습니다.");
            }
        }

        return imagesByMoment;
    }

}