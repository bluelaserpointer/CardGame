package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.BlackListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlackListServiceImpl implements BlackListService {
    @Autowired
    private BlackListDao BlackListDao;

    @Override
    public BlackList getOneBlackList(Integer BlackListId) {
        return BlackListDao.getOneBlackList(BlackListId);
    }

    public String addNewBlackList(Integer userId) {
        return BlackListDao.addNewBlackList(userId);
    }

    public String updateBlackList(Integer BlackListId, List<Integer> blockIds) {
        return BlackListDao.updateBlackList(BlackListId, blockIds);
    }

    public List<BlackList> getAllBlackLists() {
        return BlackListDao.getAllBlackLists();
    }

    public String deleteBlackLists(List<Integer> BlackListIds) {
        return BlackListDao.deleteBlackLists(BlackListIds);
    }

    public String deleteAll() {
        return BlackListDao.deleteAll();
    }
}
