package com.example.accessingdatamysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
// import org.springframework.data.jpa.repository.Query;
// import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface OwnCardRepository extends JpaRepository<OwnCard, Integer> {
    @Modifying
    @Transactional
    void deleteOwnItemByUserIdEqualsAndCardIdEquals(Integer userId, Integer cardId);
    // @Query(value = "SELECT u from OwnCards u where u.userId = ?1", nativeQuery =
    // true)
    // List<OwnCard> findByUserId(Integer userId);

}
