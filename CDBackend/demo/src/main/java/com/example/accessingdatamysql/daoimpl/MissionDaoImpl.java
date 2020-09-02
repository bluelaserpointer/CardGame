package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.GlobalConstants;
import com.example.accessingdatamysql.dao.MissionDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.io.Console;
import java.util.*;

@Repository
public class MissionDaoImpl implements MissionDao {
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private MissionDetailsRepository missionDetailsRepository;

    @Override
    public Mission getOneMission(Integer MissionId) {
        Mission Mission = missionRepository.getOne(MissionId);
        Optional<MissionDetails> MissionDetails = missionDetailsRepository
                .findMissionDetailsByMissionIdEquals(MissionId);
        MissionDetails.ifPresent(Mission::setMissionDetails);
        return Mission;
    }

    // public void modifyStorage(Mission Mission) {
    // MissionRepository.updateStorageStatus(Mission.getStorage(),
    // Mission.getMissionId());
    // return;
    // }

    // public MissionDetails findOneDetail(Integer id) {
    // return MissionDetailsRepository.findById(id);
    // }

    public Mission addNewMission(Mission newMission) {
        // GlobalConstants.printIfDoDebug(MissionDescription);
        Mission Mission = new Mission(newMission.getType(), newMission.getMissionName());
        Mission.setAwardItems(newMission.getAwardItems());
        // GlobalConstants.printIfDoDebug("new Mission has an Id of : " + n.getMissionId());
        missionRepository.save(Mission);
        MissionDetails MissionDetails = new MissionDetails(Mission.getMissionId(),
                newMission.getMissionDetails().getMissionDescription());
        missionDetailsRepository.save(MissionDetails);
        Mission.setMissionDetails(MissionDetails);
        return Mission;

    }

    public Mission updateMission(Mission updateMission) {

        Mission Mission = missionRepository.getOne(updateMission.getMissionId());
        // GlobalConstants.printIfDoDebug("old Mission has an Id of : " + n.getMissionId());
        Mission.setMission(updateMission.getType(), updateMission.getMissionName());
        Mission.setAwardItems(updateMission.getAwardItems());

        missionRepository.updateMissionStatus(Mission, updateMission.getMissionId());

        Optional<MissionDetails> optMissionDetails = missionDetailsRepository
                .findMissionDetailsByMissionIdEquals(updateMission.getMissionId());
        MissionDetails MissionDetails = new MissionDetails(updateMission.getMissionId(), "");
        if (optMissionDetails.isPresent()) {
            GlobalConstants.printIfDoDebug("Mission Exists");
            MissionDetails = optMissionDetails.get();
        } else {
            GlobalConstants.printIfDoDebug("Mission doesn't exist");
        }

        MissionDetails.setMissionDescription(updateMission.getMissionDetails().getMissionDescription());
        missionDetailsRepository.save(MissionDetails);
        Mission.setMissionDetails(MissionDetails);
        return Mission;

    }

    public List<Mission> getAllMissions() {
        List<Mission> Missions = missionRepository.findAll();
        for (int i = 0; i < Missions.size(); i++) {
            Mission Mission = Missions.get(i);
            Optional<MissionDetails> MissionDetails = missionDetailsRepository
                    .findMissionDetailsByMissionIdEquals(Mission.getMissionId());
            MissionDetails.ifPresent(Mission::setMissionDetails);
            Missions.set(i, Mission);
        }
        return Missions;
    }

    public String deleteMissions(List<Integer> MissionIds) {
        for (int i = 0; i < MissionIds.size(); i++) {
            missionRepository.deleteById(MissionIds.get(i));
            missionDetailsRepository.deleteMissionDetailsByMissionIdEquals(MissionIds.get(i));
        }
        return "Deleted Missions by id";
    }

    public String deleteAll() {
        missionRepository.deleteAll();
        missionDetailsRepository.deleteAll();
        return "Deleted All Missions";
    }

    public List<Mission> deleteMission(Integer MissionId) {
        missionRepository.deleteById(MissionId);
        missionDetailsRepository.deleteMissionDetailsByMissionIdEquals(MissionId);
        return getAllMissions();
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        return this.ListPage(page_token, page_size, missionRepository, mission -> {
            missionDetailsRepository
                    .findMissionDetailsByMissionIdEquals(mission.getMissionId())
                    .ifPresent(mission::setMissionDetails);
            return mission;
        });
    }
}
