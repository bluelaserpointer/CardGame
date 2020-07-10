package com.example.accessingdatamysql.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;
// import java.util.Optional;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BlackListRepository extends JpaRepository<BlackList, Integer> {

    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE BlackList u SET u.storage = :newstorage WHERE
    // u.BlackListId =
    // :BlackListId")
    // int updateStorageStatus(@Param("newstorage") Integer newstorage,
    // @Param("BlackListId") Integer BlackListId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE BlackList u SET u = :newBlackList WHERE u.blackListId= :BlackListId")
    int updateBlackListStatus(@Param("newBlackList") BlackList newBlackList, @Param("BlackListId") Integer BlackListId);

}