package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Activity;
import com.example.accessingdatamysql.entity.BlackList;

import java.util.List;

public interface BlackListService {
        BlackList getOneBlackList(Integer BlackListId);

        String addNewBlackList(Integer userId);

        String updateBlackList(Integer BlackListId, List<Integer> blockIds);

        List<BlackList> getAllBlackLists();

        String deleteBlackLists(List<Integer> BlackListIds);

        String deleteAll();
}
