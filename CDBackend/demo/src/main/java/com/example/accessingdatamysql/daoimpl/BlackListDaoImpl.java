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
    private BlackListRepository blackListRepository;

    @Override
    public BlackList getOneBlackList(Integer BlackListId) {
        BlackList BlackList = blackListRepository.getOne(BlackListId);
        return BlackList;
    }

    public String addNewBlackList(Integer userId) {

        BlackList BlackList = new BlackList(userId);
        // GlobalConstants.printIfDoDebug("new BlackList has an Id of : " + n.getBlackListId());
        blackListRepository.save(BlackList);
        return "Saved BlackList";

    }

    public String updateBlackList(Integer BlackListId, List<Integer> blockIds) {

        BlackList BlackList = blackListRepository.getOne(BlackListId);
        // GlobalConstants.printIfDoDebug("old BlackList has an Id of : " + n.getBlackListId());
        BlackList.setBlockIds(blockIds);
        blackListRepository.updateBlackListStatus(BlackList, BlackListId);

        return "modified BlackList: " + BlackList.getBlackListId();

    }

    public List<BlackList> getAllBlackLists() {
        List<BlackList> BlackLists = blackListRepository.findAll();
        return BlackLists;
    }

    public String deleteBlackLists(List<Integer> BlackListIds) {
        for (int i = 0; i < BlackListIds.size(); i++) {
            blackListRepository.deleteById(BlackListIds.get(i));
        }
        return "Deleted BlackLists by id";
    }

    public String deleteAll() {
        blackListRepository.deleteAll();
        return "Deleted All BlackLists";
    }
}
