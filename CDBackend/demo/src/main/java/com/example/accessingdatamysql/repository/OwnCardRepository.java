package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.Classes.PaginationJpaRepository;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

public interface OwnCardRepository extends PaginationJpaRepository<OwnCard, Integer> {
    Optional<OwnCard> findOwnCardByUserIdEqualsAndCardIdEquals(Integer userId, Integer cardId);

    @Modifying
    @Transactional
    void deleteOwnCardByUserIdEqualsAndCardIdEquals(Integer userId, Integer cardId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE OwnCard u SET u = :newOwnCard WHERE u.ownCardId = :ownCardId")
    void updateOwnCardStatus(@Param("newOwnCard") OwnCard newOwnCard, @Param("ownCardId") Integer ownCardId);

    @Transactional
    @Modifying
    @Override
    @Query(value = "SELECT * from own_card LIMIT ?1,?2", nativeQuery = true)
    List<OwnCard> ListPage(Integer start, Integer end);

}
