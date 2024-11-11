package com.MOA.backend.global.event.listener;

import com.MOA.backend.domain.image.service.S3Service;
import com.mongodb.client.model.changestream.OperationType;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoCreateListener implements ApplicationListener<ApplicationReadyEvent> {

    private final MongoTemplate mongoTemplate;
    private final S3Service s3Service;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Moment 컬렉션에 대한 Change Stream 설정
        mongoTemplate.getCollection("moment").watch()
                .forEach(change -> {
                    if(change.getOperationType() == OperationType.INSERT) {
                        Document document = change.getFullDocument();
                        Long groupId = document.getLong("groupId");
                        String momentId = document.getObjectId("_id").toHexString();

                        // S3에 LifeCycle 규칙 생성
                        s3Service.createLifecyclePolicy(groupId, momentId);
                    }
                });
    }
}
