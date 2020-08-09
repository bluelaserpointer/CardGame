package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.*;

public interface OwnItemDao {
        OwnItem getOneOwnItem(Integer OwnItemId);

        OwnItem addNewOwnItem(OwnItem newOwnItem);

        OwnItem updateOwnItem(OwnItem updateOwnItem);

        JSONObject ListPage(Integer page_token, Integer page_size);

        List<OwnItem> getAllOwnItems();

        OwnItem findOwnItemByUserIdEqualsAndItemIdEquals(Integer userId, Integer itemId);

        OwnItem repeatOwnItem(OwnItem ownItem, Integer itemCount);

        List<OwnItem> getAllOwnItemsByUserId(Integer userId);

        String deleteOwnItems(List<Integer> OwnItemIds);

        String deleteAll();

        List<OwnItem> deleteOwnItem(Integer userId, Integer itemId);

        OwnItem addNewOwnItem(Integer userId, Integer itemId, Integer itemAmount);
}
