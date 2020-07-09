package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.FriendListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FriendListServiceImpl implements FriendListService {
    @Autowired
    private FriendListDao FriendListDao;

    @Override
    public FriendList getOneFriendList(Integer FriendListId) {
        return FriendListDao.getOneFriendList(FriendListId);
    }

    public String addNewFriendList(Integer userId) {
        return FriendListDao.addNewFriendList(userId);
    }

    public String updateFriendList(Integer FriendListId, List<Integer> friendIds) {
        return FriendListDao.updateFriendList(FriendListId, friendIds);
    }

    public List<FriendList> getAllFriendLists() {
        return FriendListDao.getAllFriendLists();
    }

    public String deleteFriendLists(List<Integer> FriendListIds) {
        return FriendListDao.deleteFriendLists(FriendListIds);
    }

    public String deleteAll() {
        return FriendListDao.deleteAll();
    }
}