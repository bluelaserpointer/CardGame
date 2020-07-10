package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.MailDetails;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MailDetailsRepository extends MongoRepository<MailDetails, Integer> {
    Optional<MailDetails> findMailDetailsByMailIdEquals(Integer mailId);

    @Modifying
    @Transactional
    void deleteMailDetailsByMailIdEquals(Integer mailId);
}
