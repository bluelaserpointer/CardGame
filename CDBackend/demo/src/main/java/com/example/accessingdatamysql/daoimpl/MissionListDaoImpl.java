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
    private MissionListRepository missionListRepository;

    @Override
    public MissionList getOneMissionList(Integer MissionListId) {
        return missionListRepository.getOne(MissionListId);
    }

    public String addNewMissionList(Integer userId) {

        MissionList MissionList = new MissionList(userId);
        // System.out.println("new MissionList has an Id of : " + n.getMissionListId());
        missionListRepository.save(MissionList);
        return "Saved MissionList";

    }

    public String updateMissionList(Integer MissionListId, List<Mission> Missions) {

        MissionList MissionList = missionListRepository.getOne(MissionListId);
        // System.out.println("old MissionList has an Id of : " + n.getMissionListId());
        MissionList.setMissions(Missions);

        missionListRepository.updateMissionListStatus(MissionList, MissionListId);
        return "modified MissionList: " + MissionList.getMissionListId();

    }

    public List<MissionList> getAllMissionLists() {
        return missionListRepository.findAll();
    }

    public String deleteMissionLists(List<Integer> MissionListIds) {
        for (Integer missionListId : MissionListIds) {
            missionListRepository.deleteById(missionListId);
        }
        return "Deleted MissionLists by id";
    }

    public String deleteAll() {
        missionListRepository.deleteAll();
        return "Deleted All MissionLists";
    }

    public List<MissionList> deleteMissionList(Integer MissionListId) {
        missionListRepository.deleteById(MissionListId);
        return getAllMissionLists();
    }
}
