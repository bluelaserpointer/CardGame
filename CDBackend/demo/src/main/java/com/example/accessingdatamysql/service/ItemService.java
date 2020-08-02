package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Item;
import com.example.accessingdatamysql.entity.ListRequest;

import java.util.List;

public interface ItemService {
        Item getOneItem(Integer ItemId);

        Item addNewItem(Item newItem);

        Item updateItem(Item updateItem);

        // 获取指定页数的Item
        List<Item> ListPage(ListRequest listRequest);

        List<Item> getAllItems();

        String deleteItems(List<Integer> ItemIds);

        String deleteAll();

        List<Item> deleteItem(Integer itemId);

}
