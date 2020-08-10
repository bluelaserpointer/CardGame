package com.example.accessingdatamysql.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.accessingdatamysql.Classes.PaginationJpaRepository;
import com.example.accessingdatamysql.Classes.RepositoryPagination;
import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;
// import java.util.Optional;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CardRepository extends PaginationJpaRepository<Card, Integer> {

    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE Card u SET u.storage = :newstorage WHERE u.CardId =
    // :CardId")
    // int updateStorageStatus(@Param("newstorage") Integer newstorage,
    // @Param("CardId") Integer CardId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE card u SET u = :newCard WHERE u.cardId = :CardId")
    int updateCardStatus(@Param("newCard") Card newCard, @Param("CardId") Integer CardId);

    @Transactional
    @Modifying
    @Query(value = "SELECT * from card LIMIT ?1,?2", nativeQuery = true)
    List<Card> ListPage(Integer start, Integer amount);

    @Transactional
    @Modifying
    @Query(value = "SELECT * from card where rarity = :rarity AND type = :type", nativeQuery = true)
    List<Card> getByRarityAndType(String rarity, Integer type);
}
