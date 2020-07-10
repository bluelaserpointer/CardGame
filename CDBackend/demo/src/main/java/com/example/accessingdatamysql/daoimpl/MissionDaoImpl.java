package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.ItemDao;
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
    private MissionRepository MissionRepository;
    @Autowired
    private ItemDao ItemDao;
    @Autowired
    private MissionDetailsRepository MissionDetailsRepository;

    @Override
    public Mission getOneMission(Integer MissionId) {
        Mission Mission = MissionRepository.getOne(MissionId);
        Optional<MissionDetails> MissionDetails = MissionDetailsRepository
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

    public String addNewMission(String type, String MissionName, String MissionDescription,
            List<Integer> awardItemIds) {
        // System.out.println(MissionDescription);
        Mission Mission = new Mission(type, MissionName);
        Mission.setAwardItems(awardItemIds);
        // System.out.println("new Mission has an Id of : " + n.getMissionId());
        MissionRepository.save(Mission);
        MissionDetails MissionDetails = new MissionDetails(Mission.getMissionId(), MissionDescription);
        MissionDetailsRepository.save(MissionDetails);
        return "Saved Mission";

    }

    public String updateMission(Integer MissionId, String type, String MissionName, String MissionDescription,
            List<Integer> awardItemIds) {

        Mission Mission = MissionRepository.getOne(MissionId);
        // System.out.println("old Mission has an Id of : " + n.getMissionId());
        Mission.setMission(type, MissionName);
        Mission.setAwardItems(awardItemIds);

        MissionRepository.updateMissionStatus(Mission, MissionId);

        Optional<MissionDetails> optMissionDetails = MissionDetailsRepository
                .findMissionDetailsByMissionIdEquals(MissionId);
        MissionDetails MissionDetails = new MissionDetails(MissionId, "");
        if (optMissionDetails.isPresent()) {
            System.out.println("Mission Exists");
            MissionDetails = optMissionDetails.get();
        } else {
            System.out.println("Mission doesn't exist");
        }

        MissionDetails.setMissionDescription(MissionDescription);
        MissionDetailsRepository.save(MissionDetails);
        return "modified Mission: " + Mission.getMissionName();

    }

    public List<Mission> getAllMissions() {
        List<Mission> Missions = MissionRepository.findAll();
        for (int i = 0; i < Missions.size(); i++) {
            Mission Mission = Missions.get(i);
            Optional<MissionDetails> MissionDetails = MissionDetailsRepository
                    .findMissionDetailsByMissionIdEquals(Mission.getMissionId());
            MissionDetails.ifPresent(Mission::setMissionDetails);
            Missions.set(i, Mission);
        }
        return Missions;
    }

    public String deleteMissions(List<Integer> MissionIds) {
        for (int i = 0; i < MissionIds.size(); i++) {
            MissionRepository.deleteById(MissionIds.get(i));
            MissionDetailsRepository.deleteMissionDetailsByMissionIdEquals(MissionIds.get(i));
        }
        return "Deleted Missions by id";
    }

    public String deleteAll() {
        MissionRepository.deleteAll();
        MissionDetailsRepository.deleteAll();
        return "Deleted All Missions";
    }

    public List<Mission> deleteMission(Integer MissionId) {
        MissionRepository.deleteById(MissionId);
        MissionDetailsRepository.deleteMissionDetailsByMissionIdEquals(MissionId);
        return getAllMissions();
    }
}
