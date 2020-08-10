package com.example.accessingdatamysql.dao;

import java.util.List;

import com.example.accessingdatamysql.Classes.DaoPagination;
import com.example.accessingdatamysql.entity.*;

public interface OwnItemDao extends DaoPagination {
        OwnItem getOneOwnItem(Integer OwnItemId);

        OwnItem addNewOwnItem(OwnItem newOwnItem);

        OwnItem updateOwnItem(OwnItem updateOwnItem);

        List<OwnItem> getAllOwnItems();

        OwnItem findOwnItemByUserIdEqualsAndItemIdEquals(Integer userId, Integer itemId);

        OwnItem repeatOwnItem(OwnItem ownItem, Integer itemCount);

        List<OwnItem> getAllOwnItemsByUserId(Integer userId);

        String deleteOwnItems(List<Integer> OwnItemIds);

        String deleteAll();

        List<OwnItem> deleteOwnItem(Integer userId, Integer itemId);

        OwnItem addNewOwnItem(Integer userId, Integer itemId, Integer itemAmount);
}
