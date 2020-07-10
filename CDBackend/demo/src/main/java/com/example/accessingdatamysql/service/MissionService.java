package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.*;

import java.util.List;

public interface MissionService {
        Mission getOneMission(Integer MissionId);

        // Optional<MissionDetails> findOneDetail(Integer id);

        // void modifyStorage(Mission Mission);

        String addNewMission(String type, String MissionName, String MissionDescription, List<Integer> awardItemIds);

        String updateMission(Integer MissionId, String type, String MissionName, String MissionDescription,
                        List<Integer> awardItemIds);

        List<Mission> getAllMissions();

        String deleteMissions(List<Integer> MissionIds);

        String deleteAll();

        List<Mission> deleteMission(Integer MissionId);

}
