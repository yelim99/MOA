package com.MOA.backend.domain.moment.repository;

import com.MOA.backend.domain.moment.entity.DeletedMoment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("mongoDeletedMomentRepository")
public interface DeletedMomentRepository extends MongoRepository<DeletedMoment, String> {

    /**
     * 현재 시간
     * @param currentDate
     * @return
     */
    List<DeletedMoment> findAllByExpiredAtBefore(Date currentDate);

    Void deleteByExpiredAtBefore(Date currentDate);
}
