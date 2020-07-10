package com.example.accessingdatamysql.serviceimpl;

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

    public String addNewItem(String itemName, Integer price, String itemImg, String itemDescription) {
        return ItemDao.addNewItem(itemName, price, itemImg, itemDescription);
    }

    public String updateItem(Integer ItemId, String itemName, Integer price, String itemImg, String itemDescription) {
        return ItemDao.updateItem(ItemId, itemName, price, itemImg, itemDescription);
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
}
