package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.FriendListDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.io.Console;
import java.util.*;

@Repository
public class FriendListDaoImpl implements FriendListDao {
    @Autowired
    private FriendListRepository friendListRepository;

    @Override
    public FriendList getOneFriendList(Integer FriendListId) {
        FriendList FriendList = friendListRepository.getOne(FriendListId);
        return FriendList;
    }

    // public void modifyStorage(FriendList FriendList) {
    // FriendListRepository.updateStorageStatus(FriendList.getStorage(),
    // FriendList.getFriendListId());
    // return;
    // }

    // public FriendListDetails findOneDetail(Integer id) {
    // return FriendListDetailsRepository.findById(id);
    // }

    public String addNewFriendList(Integer userId) {

        FriendList FriendList = new FriendList(userId);
        // System.out.println("new FriendList has an Id of : " + n.getFriendListId());
        friendListRepository.save(FriendList);
        return "Saved FriendList";

    }

    public String updateFriendList(Integer FriendListId, List<Integer> friendIds) {

        FriendList FriendList = friendListRepository.getOne(FriendListId);
        // System.out.println("old FriendList has an Id of : " + n.getFriendListId());
        FriendList.setFriendIds(friendIds);
        friendListRepository.updateFriendListStatus(FriendList, FriendListId);
        // return "Modified FriendList";

        return "modified FriendList: " + FriendList.getFriendListId();

    }

    public List<FriendList> getAllFriendLists() {
        List<FriendList> FriendLists = friendListRepository.findAll();
        return FriendLists;
    }

    public String deleteFriendLists(List<Integer> FriendListIds) {
        for (int i = 0; i < FriendListIds.size(); i++) {
            friendListRepository.deleteById(FriendListIds.get(i));
        }
        return "Deleted FriendLists by id";
    }

    public String deleteAll() {
        friendListRepository.deleteAll();
        return "Deleted All FriendLists";
    }
}
