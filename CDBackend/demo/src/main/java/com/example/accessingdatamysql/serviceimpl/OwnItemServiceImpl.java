package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.OwnItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OwnItemServiceImpl implements OwnItemService {
    @Autowired
    private OwnItemDao OwnItemDao;

    @Override
    public OwnItem getOneOwnItem(Integer OwnItemId) {
        return OwnItemDao.getOneOwnItem(OwnItemId);
    }

    public String addNewOwnItem(Integer userId, Integer itemId, Integer ItemCount) {
        return OwnItemDao.addNewOwnItem(userId, itemId, ItemCount);
    }

    public List<OwnItem> getAllOwnItemsByUserId(Integer userId) {
        return OwnItemDao.getAllOwnItemsByUserId(userId);
    }

    public String updateOwnItem(Integer OwnItemId, Integer userId, Integer itemId, Integer ItemCount) {
        return OwnItemDao.updateOwnItem(OwnItemId, userId, itemId, ItemCount);
    }

    public List<OwnItem> getAllOwnItems() {
        return OwnItemDao.getAllOwnItems();
    }

    public String deleteOwnItems(List<Integer> OwnItemIds) {
        return OwnItemDao.deleteOwnItems(OwnItemIds);
    }

    public String deleteAll() {
        return OwnItemDao.deleteAll();
    }

    public List<OwnItem> deleteOwnItem(Integer userId, Integer itemId){
        return OwnItemDao.deleteOwnItem(userId, itemId);
    }
}
