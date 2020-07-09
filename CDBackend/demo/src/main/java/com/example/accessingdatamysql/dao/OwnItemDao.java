package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface OwnItemDao {
        OwnItem getOneOwnItem(Integer OwnItemId);

        String addNewOwnItem(Integer userId, Integer itemId, Integer ItemCount);

        String updateOwnItem(Integer OwnItemId, Integer userId, Integer itemId, Integer ItemCount);

        List<OwnItem> getAllOwnItems();

        List<OwnItem> getAllOwnItemsByUserId(Integer userId);

        String deleteOwnItems(List<Integer> OwnItemIds);

        String deleteAll();

        List<OwnItem> deleteOwnItem(Integer userId, Integer itemId);

}
