package com.MOA.backend.domain.moment.repository;

import com.MOA.backend.domain.moment.entity.Moment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository("mongoMomentRepository")
public interface MomentRepository extends MongoRepository<Moment, String> {

}
