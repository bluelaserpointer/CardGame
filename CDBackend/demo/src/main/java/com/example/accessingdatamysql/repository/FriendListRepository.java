package com.example.accessingdatamysql.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface FriendListRepository extends JpaRepository<FriendList, Integer> {

    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE FriendList u SET u.storage = :newstorage WHERE
    // u.FriendListId =
    // :FriendListId")
    // int updateStorageStatus(@Param("newstorage") Integer newstorage,
    // @Param("FriendListId") Integer FriendListId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE friend_list u SET u = :newFriendList WHERE u.friendListId = :FriendListId")
    int updateFriendListStatus(@Param("newFriendList") FriendList newFriendList,
            @Param("FriendListId") Integer FriendListId);

}
