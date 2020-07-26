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

    public OwnItem addNewOwnItem(OwnItem newOwnItem) {
        if (OwnItemDao.findOwnItemByUserIdEqualsAndItemIdEquals(newOwnItem.getUserId(),
                newOwnItem.getItemId()) != null) {
            // 用户本身就拥有该道具
            return OwnItemDao.repeatOwnItem(newOwnItem);

        }
        return OwnItemDao.addNewOwnItem(newOwnItem);
    }

    public List<OwnItem> getAllOwnItemsByUserId(Integer userId) {
        return OwnItemDao.getAllOwnItemsByUserId(userId);
    }

    public OwnItem updateOwnItem(OwnItem updateOwnItem) {
        return OwnItemDao.updateOwnItem(updateOwnItem);
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

    public List<OwnItem> deleteOwnItem(Integer userId, Integer itemId) {
        return OwnItemDao.deleteOwnItem(userId, itemId);
    }
}
