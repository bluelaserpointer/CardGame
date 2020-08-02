package com.example.accessingdatamysql.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao ItemDao;

    @Override
    public Item getOneItem(Integer ItemId) {
        return ItemDao.getOneItem(ItemId);
    }

    public Item addNewItem(Item newItem) {
        return ItemDao.addNewItem(newItem);
    }

    public Item updateItem(Item updateItem) {
        return ItemDao.updateItem(updateItem);
    }

    public List<Item> getAllItems() {
        return ItemDao.getAllItems();
    }

    public String deleteItems(List<Integer> ItemIds) {
        return ItemDao.deleteItems(ItemIds);
    }

    public String deleteAll() {
        return ItemDao.deleteAll();
    }

    public List<Item> deleteItem(Integer itemId) {
        return ItemDao.deleteItem(itemId);
    }

    @Override
    public JSONObject ListPage(ListRequest listRequest) {
        return ItemDao.ListPage(listRequest.getPageToken(), listRequest.getPageSize());
    }
}
