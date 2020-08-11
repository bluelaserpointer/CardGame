package com.example.accessingdatamysql.dao;

import java.util.List;

import com.example.accessingdatamysql.Classes.PaginationDao;
import com.example.accessingdatamysql.entity.*;

public interface MissionDao extends PaginationDao {
        Mission getOneMission(Integer MissionId);

        // Optional<MissionDetails> findOneDetail(Integer id);

        // void modifyStorage(Mission Mission);

        Mission addNewMission(Mission newMission);

        Mission updateMission(Mission updateMission);

        List<Mission> getAllMissions();

        String deleteMissions(List<Integer> MissionIds);

        String deleteAll();

        List<Mission> deleteMission(Integer MissionId);

}
