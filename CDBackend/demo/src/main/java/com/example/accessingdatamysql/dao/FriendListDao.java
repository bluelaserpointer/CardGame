package com.example.accessingdatamysql.dao;

import java.util.List;

import com.example.accessingdatamysql.entity.*;

public interface FriendListDao {
        FriendList getOneFriendList(Integer FriendListId);

        String addNewFriendList(Integer userId);

        String updateFriendList(Integer FriendListId, List<Integer> friendIds);

        List<FriendList> getAllFriendLists();

        String deleteFriendLists(List<Integer> FriendListIds);

        String deleteAll();

}
