package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ActivityDetails;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ActivityDetailsRepository extends MongoRepository<ActivityDetails, Integer> {

    @Transactional
    Optional<ActivityDetails> findActivityDetailsByActivityIdEquals(Integer activityId);

    @Modifying
    @Transactional
    void deleteActivityDetailsByActivityIdEquals(Integer activityId);
}
