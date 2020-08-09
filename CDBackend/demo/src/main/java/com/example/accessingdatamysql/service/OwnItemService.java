package com.example.accessingdatamysql.service;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.*;

import java.util.List;

public interface OwnItemService {
        OwnItem getOneOwnItem(Integer OwnItemId);

        OwnItem addNewOwnItem(OwnItem newOwnItem);

        OwnItem updateOwnItem(OwnItem updateOwnItem);

        // 获取指定页数的OwnItem
        JSONObject ListPage(ListRequest listRequest);

        List<OwnItem> getAllOwnItems();

        List<OwnItem> getAllOwnItemsByUserId(Integer userId);

        String deleteOwnItems(List<Integer> OwnItemIds);

        String deleteAll();

        List<OwnItem> deleteOwnItem(Integer userId, Integer itemId);

        OwnItem addNewOwnItem(Integer userId, Integer itemId, Integer itemAmount);
}
