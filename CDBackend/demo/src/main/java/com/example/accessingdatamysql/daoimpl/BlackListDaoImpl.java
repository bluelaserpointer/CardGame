package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.BlackListDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.io.Console;
import java.util.*;

@Repository
public class BlackListDaoImpl implements BlackListDao {
    @Autowired
    private BlackListRepository BlackListRepository;

    @Override
    public BlackList getOneBlackList(Integer BlackListId) {
        BlackList BlackList = BlackListRepository.getOne(BlackListId);
        return BlackList;
    }

    public String addNewBlackList(Integer userId) {

        BlackList BlackList = new BlackList(userId);
        // System.out.println("new BlackList has an Id of : " + n.getBlackListId());
        BlackListRepository.save(BlackList);
        return "Saved BlackList";

    }

    public String updateBlackList(Integer BlackListId, List<Integer> blockIds) {

        BlackList BlackList = BlackListRepository.getOne(BlackListId);
        // System.out.println("old BlackList has an Id of : " + n.getBlackListId());
        BlackList.setBlockIds(blockIds);
        BlackListRepository.updateBlackListStatus(BlackList, BlackListId);

        return "modified BlackList: " + BlackList.getBlackListId();

    }

    public List<BlackList> getAllBlackLists() {
        List<BlackList> BlackLists = BlackListRepository.findAll();
        return BlackLists;
    }

    public String deleteBlackLists(List<Integer> BlackListIds) {
        for (int i = 0; i < BlackListIds.size(); i++) {
            BlackListRepository.deleteById(BlackListIds.get(i));
        }
        return "Deleted BlackLists by id";
    }

    public String deleteAll() {
        BlackListRepository.deleteAll();
        return "Deleted All BlackLists";
    }
}