package com.example.accessingdatamysql.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.accessingdatamysql.Classes.Pagination;
import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;
// import java.util.Optional;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ItemRepository extends JpaRepository<Item, Integer>, Pagination<Item> {

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
    @Modifying
    @Query(value = "SELECT * from Item LIMIT ?1,?2", nativeQuery = true)
    @Override
    List<Item> ListPage(Integer start, Integer end);

}
