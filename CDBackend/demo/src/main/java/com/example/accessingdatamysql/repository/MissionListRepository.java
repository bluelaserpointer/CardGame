package com.example.accessingdatamysql.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;
// import java.util.Optional;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MissionListRepository extends JpaRepository<MissionList, Integer> {

    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE MissionList u SET u.storage = :newstorage WHERE
    // u.MissionListId =
    // :MissionListId")
    // int updateStorageStatus(@Param("newstorage") Integer newstorage,
    // @Param("MissionListId") Integer MissionListId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE mission_list u SET u = :newMissionList WHERE u.missionListId = :MissionListId")
    int updateMissionListStatus(@Param("newMissionList") MissionList newMissionList,
            @Param("MissionListId") Integer MissionListId);

}