package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.MissionListDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.io.Console;
import java.util.*;

@Repository
public class MissionListDaoImpl implements MissionListDao {
    @Autowired
    private MissionListRepository MissionListRepository;

    @Override
    public MissionList getOneMissionList(Integer MissionListId) {
        MissionList MissionList = MissionListRepository.getOne(MissionListId);
        return MissionList;
    }

    public String addNewMissionList(Integer userId) {

        MissionList MissionList = new MissionList(userId);
        // System.out.println("new MissionList has an Id of : " + n.getMissionListId());
        MissionListRepository.save(MissionList);
        return "Saved MissionList";

    }

    public String updateMissionList(Integer MissionListId, List<Mission> Missions) {

        MissionList MissionList = MissionListRepository.getOne(MissionListId);
        // System.out.println("old MissionList has an Id of : " + n.getMissionListId());
        MissionList.setMissions(Missions);

        MissionListRepository.updateMissionListStatus(MissionList, MissionListId);
        return "modified MissionList: " + MissionList.getMissionListId();

    }

    public List<MissionList> getAllMissionLists() {
        List<MissionList> MissionLists = MissionListRepository.findAll();
        return MissionLists;
    }

    public String deleteMissionLists(List<Integer> MissionListIds) {
        for (int i = 0; i < MissionListIds.size(); i++) {
            MissionListRepository.deleteById(MissionListIds.get(i));
        }
        return "Deleted MissionLists by id";
    }

    public String deleteAll() {
        MissionListRepository.deleteAll();
        return "Deleted All MissionLists";
    }

    public List<MissionList> deleteMissionList(Integer MissionListId) {
        MissionListRepository.deleteById(MissionListId);
        return getAllMissionLists();
    }
}