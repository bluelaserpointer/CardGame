package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ItemDetails;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ItemDetailsRepository extends MongoRepository<ItemDetails, Integer> {
    Optional<ItemDetails> findItemDetailsByItemIdEquals(Integer itemId);

    @Modifying
    @Transactional
    void deleteItemDetailsByItemIdEquals(Integer itemId);
}
