package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Card;
import com.example.accessingdatamysql.entity.Item;

import java.util.List;

public interface ItemService {
        Item getOneItem(Integer ItemId);

        String addNewItem(String itemName, Integer price, String itemImg, String itemDescription);

        String updateItem(Integer ItemId, String itemName, Integer price, String itemImg, String itemDescription);

        List<Item> getAllItems();

        String deleteItems(List<Integer> ItemIds);

        String deleteAll();

        List<Item> deleteItem(Integer itemId);

}
