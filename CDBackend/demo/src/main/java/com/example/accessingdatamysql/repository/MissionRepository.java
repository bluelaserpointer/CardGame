package com.example.accessingdatamysql.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.accessingdatamysql.Classes.ListPagination;
import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;
// import java.util.Optional;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MissionRepository extends JpaRepository<Mission, Integer>, ListPagination<Mission> {

    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE Mission u SET u.storage = :newstorage WHERE
    // u.MissionId =
    // :MissionId")
    // int updateStorageStatus(@Param("newstorage") Integer newstorage,
    // @Param("MissionId") Integer MissionId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE mission u SET u = :newMission WHERE u.missionId = :MissionId")
    int updateMissionStatus(@Param("newMission") Mission newMission, @Param("MissionId") Integer MissionId);

    @Transactional
    @Modifying
    @Query(value = "SELECT * from mission LIMIT ?1,?2", nativeQuery = true)
    List<Mission> ListPage(Integer start, Integer end);

}
