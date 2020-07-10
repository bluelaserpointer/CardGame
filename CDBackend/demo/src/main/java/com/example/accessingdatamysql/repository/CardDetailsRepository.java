package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.CardDetails;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CardDetailsRepository extends MongoRepository<CardDetails, Integer> {
    Optional<CardDetails> findCardDetailsByCardIdEquals(Integer cardId);

    @Modifying
    @Transactional
    void deleteCardDetailsByCardIdEquals(Integer cardId);
}
