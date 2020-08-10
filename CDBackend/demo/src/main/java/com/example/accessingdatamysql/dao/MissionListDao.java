package com.example.accessingdatamysql.dao;

import java.util.List;

import com.example.accessingdatamysql.entity.*;

public interface MissionListDao {
        MissionList getOneMissionList(Integer MissionListId);

        String addNewMissionList(Integer userId);

        String updateMissionList(Integer MissionListId, List<Mission> Missions);

        List<MissionList> getAllMissionLists();

        String deleteMissionLists(List<Integer> MissionListIds);

        String deleteAll();

        List<MissionList> deleteMissionList(Integer MissionListId);

}
