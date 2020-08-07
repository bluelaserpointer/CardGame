package com.example.accessingdatamysql.service;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.*;

import java.util.List;

public interface MissionService {
        Mission getOneMission(Integer MissionId);

        // Optional<MissionDetails> findOneDetail(Integer id);

        // void modifyStorage(Mission Mission);

        Mission addNewMission(Mission newMission);

        Mission updateMission(Mission updateMission);

        // 获取指定页数的Mission
        JSONObject ListPage(ListRequest listRequest);

        List<Mission> getAllMissions();

        String deleteMissions(List<Integer> MissionIds);

        String deleteAll();

        List<Mission> deleteMission(Integer MissionId);

}
