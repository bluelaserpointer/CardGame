package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.*;

import java.util.List;

public interface MissionListService {
        MissionList getOneMissionList(Integer MissionListId);

        String addNewMissionList(Integer userId);

        String updateMissionList(Integer MissionListId, List<Mission> Missions);

        List<MissionList> getAllMissionLists();

        String deleteMissionLists(List<Integer> MissionListIds);

        String deleteAll();

        List<MissionList> deleteMissionList(Integer MissionListId);
}