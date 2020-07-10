package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.MissionDetails;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MissionDetailsRepository extends MongoRepository<MissionDetails, Integer> {
    Optional<MissionDetails> findMissionDetailsByMissionIdEquals(Integer MissionId);

    @Modifying
    @Transactional
    void deleteMissionDetailsByMissionIdEquals(Integer MissionId);
}
