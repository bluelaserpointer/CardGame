package com.example.accessingdatamysql.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.accessingdatamysql.Classes.PaginationJpaRepository;
import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ActivityRepository extends PaginationJpaRepository<Activity, Integer> {

    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE Activity u SET u.storage = :newstorage WHERE
    // u.ActivityId =
    // :ActivityId")
    // int updateStorageStatus(@Param("newstorage") Integer newstorage,
    // @Param("ActivityId") Integer ActivityId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Activity u SET u = :newActivity WHERE u.activityId = :activityId")
    void updateActivityStatus(@Param("newActivity") Activity newActivity, @Param("activityId") Integer ActivityId);

    @Transactional
    @Modifying
    @Query(value = "SELECT * from activity LIMIT ?1,?2", nativeQuery = true)
    List<Activity> ListPage(Integer start, Integer end);

}
