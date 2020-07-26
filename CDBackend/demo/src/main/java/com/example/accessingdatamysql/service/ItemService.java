package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Item;

import java.util.List;

public interface ItemService {
        Item getOneItem(Integer ItemId);

        Item addNewItem(Item newItem);

        Item updateItem(Item updateItem);

        List<Item> getAllItems();

        String deleteItems(List<Integer> ItemIds);

        String deleteAll();

        List<Item> deleteItem(Integer itemId);

}
