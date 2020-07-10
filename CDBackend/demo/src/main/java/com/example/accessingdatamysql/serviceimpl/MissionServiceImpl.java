package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.MissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MissionServiceImpl implements MissionService {
    @Autowired
    private MissionDao MissionDao;

    @Override
    public Mission getOneMission(Integer MissionId) {
        return MissionDao.getOneMission(MissionId);
    }

    public String addNewMission(String type, String MissionName, String MissionDescription,
            List<Integer> awardItemIds) {
        return MissionDao.addNewMission(type, MissionName, MissionDescription, awardItemIds);
    }

    public String updateMission(Integer MissionId, String type, String MissionName, String MissionDescription,
            List<Integer> awardItemIds) {
        return MissionDao.updateMission(MissionId, type, MissionName, MissionDescription, awardItemIds);
    }

    public List<Mission> getAllMissions() {
        return MissionDao.getAllMissions();
    }

    public String deleteMissions(List<Integer> MissionIds) {
        return MissionDao.deleteMissions(MissionIds);
    }

    public String deleteAll() {
        return MissionDao.deleteAll();
    }

    public List<Mission> deleteMission(Integer MissionId) {
        return MissionDao.deleteMission(MissionId);
    }
}
