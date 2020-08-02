package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface MissionDao {
        Mission getOneMission(Integer MissionId);

        // Optional<MissionDetails> findOneDetail(Integer id);

        // void modifyStorage(Mission Mission);

        Mission addNewMission(Mission newMission);

        Mission updateMission(Mission updateMission);

        List<Mission> ListPage(Integer page_token, Integer page_size);

        List<Mission> getAllMissions();

        String deleteMissions(List<Integer> MissionIds);

        String deleteAll();

        List<Mission> deleteMission(Integer MissionId);

}
