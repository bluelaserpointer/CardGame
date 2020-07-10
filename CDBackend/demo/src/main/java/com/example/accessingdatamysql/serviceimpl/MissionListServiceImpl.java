package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.MissionListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MissionListServiceImpl implements MissionListService {
    @Autowired
    private MissionListDao MissionListDao;

    @Override
    public MissionList getOneMissionList(Integer MissionListId) {
        return MissionListDao.getOneMissionList(MissionListId);
    }

    public String addNewMissionList(Integer userId) {
        return MissionListDao.addNewMissionList(userId);
    }

    public String updateMissionList(Integer MissionListId, List<Mission> Missions) {
        return MissionListDao.updateMissionList(MissionListId, Missions);
    }

    public List<MissionList> getAllMissionLists() {
        return MissionListDao.getAllMissionLists();
    }

    public String deleteMissionLists(List<Integer> MissionListIds) {
        return MissionListDao.deleteMissionLists(MissionListIds);
    }

    public String deleteAll() {
        return MissionListDao.deleteAll();
    }

    public List<MissionList> deleteMissionList(Integer MissionListId) {
        return MissionListDao.deleteMissionList(MissionListId);
    }
}