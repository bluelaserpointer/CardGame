package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface OwnItemDao {
        OwnItem getOneOwnItem(Integer OwnItemId);

        OwnItem addNewOwnItem(Integer userId, Integer itemId, Integer ItemCount);

        OwnItem updateOwnItem(Integer OwnItemId, Integer userId, Integer itemId, Integer ItemCount);

        List<OwnItem> getAllOwnItems();

        OwnItem findOwnItemByUserIdEqualsAndItemIdEquals(Integer userId, Integer itemId);

        OwnItem repeatOwnItem(OwnItem ownItem, Integer itemCount);

        List<OwnItem> getAllOwnItemsByUserId(Integer userId);

        String deleteOwnItems(List<Integer> OwnItemIds);

        String deleteAll();

        List<OwnItem> deleteOwnItem(Integer userId, Integer itemId);

}
