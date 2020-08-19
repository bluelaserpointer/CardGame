package com.example.accessingdatamysql.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.accessingdatamysql.Classes.PaginationJpaRepository;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.*;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface OwnItemRepository extends PaginationJpaRepository<OwnItem, Integer> {
    @Modifying
    @Transactional
    void deleteOwnItemByUserIdEqualsAndItemIdEquals(Integer userId, Integer itemId);

    Optional<OwnItem> findOwnItemByUserIdEqualsAndItemIdEquals(Integer userId, Integer itemId);

    // @Query(value = "SELECT u from OwnItems u where u.userId = ?1", nativeQuery =
    // true)
    // List<OwnItem> findByUserId(Integer userId);
    @Transactional
    @Modifying
    @Query(value = "UPDATE OwnItem u SET u = :newOwnItem WHERE u.ownItemId = :OwnItemId")
    void updateOwnItemStatus(@Param("newOwnItem") OwnItem newOwnItem, @Param("OwnItemId") Integer OwnItemId);

    @Transactional
    @Override
    @Query(value = "SELECT * from own_item LIMIT ?1,?2", nativeQuery = true)
    List<OwnItem> ListPage(Integer start, Integer end);

    @Transactional
    List<OwnItem> findAllByUserIdEquals(Integer userId);
}
