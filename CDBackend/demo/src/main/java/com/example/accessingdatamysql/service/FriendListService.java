package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.FriendList;

import java.util.List;

public interface FriendListService {
        FriendList getOneFriendList(Integer FriendListId);

        String addNewFriendList(Integer userId);

        String updateFriendList(Integer FriendListId, List<Integer> friendIds);

        List<FriendList> getAllFriendLists();

        String deleteFriendLists(List<Integer> FriendListIds);

        String deleteAll();
}