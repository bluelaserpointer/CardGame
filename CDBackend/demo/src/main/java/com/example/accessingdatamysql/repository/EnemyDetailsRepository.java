package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.EnemyDetails;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface EnemyDetailsRepository extends MongoRepository<EnemyDetails, Integer> {
    Optional<EnemyDetails> findEnemyDetailsByEnemyIdEquals(Integer EnemyId);

    @Modifying
    @Transactional
    void deleteEnemyDetailsByEnemyIdEquals(Integer EnemyId);
}
