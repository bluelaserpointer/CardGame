package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.*;

import java.util.List;

public interface OwnItemService {
        OwnItem getOneOwnItem(Integer OwnItemId);

        OwnItem addNewOwnItem(OwnItem newOwnItem);

        OwnItem updateOwnItem(OwnItem updateOwnItem);

        List<OwnItem> getAllOwnItems();

        List<OwnItem> getAllOwnItemsByUserId(Integer userId);

        String deleteOwnItems(List<Integer> OwnItemIds);

        String deleteAll();

        List<OwnItem> deleteOwnItem(Integer userId, Integer itemId);
}
