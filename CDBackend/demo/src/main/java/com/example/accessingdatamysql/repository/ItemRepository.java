package com.example.accessingdatamysql.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.accessingdatamysql.Classes.PaginationJpaRepository;
import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ItemRepository extends PaginationJpaRepository<Item, Integer> {

    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE Item u SET u.storage = :newstorage WHERE u.ItemId =
    // :ItemId")
    // int updateStorageStatus(@Param("newstorage") Integer newstorage,
    // @Param("ItemId") Integer ItemId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Item u SET u = :newItem WHERE u.itemId = :ItemId")
    int updateItemStatus(@Param("newItem") Item newItem, @Param("ItemId") Integer ItemId);

    @Transactional
    @Query(value = "SELECT * from item LIMIT ?1,?2", nativeQuery = true)
    List<Item> ListPage(Integer start, Integer end);

}
