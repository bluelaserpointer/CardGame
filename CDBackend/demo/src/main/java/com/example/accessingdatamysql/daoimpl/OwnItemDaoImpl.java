package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.OwnItemDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
// import java.io.Console;
import java.util.*;

@Repository
public class OwnItemDaoImpl implements OwnItemDao {
    @Autowired
    private OwnItemRepository ownItemRepository;

    @Override
    public OwnItem getOneOwnItem(Integer OwnItemId) {
        return ownItemRepository.getOne(OwnItemId);
    }

    public OwnItem addNewOwnItem(OwnItem newOwnItem) {
        final Timestamp acquireDate = new Timestamp(System.currentTimeMillis());

        final OwnItem ownItem = new OwnItem(newOwnItem.getUserId(), newOwnItem.getItemId(), newOwnItem.getItemCount(),
                acquireDate);
        // GlobalConstants.printIfDoDebug("new OwnItem has an Id of : " + n.getOwnItemId());
        ownItemRepository.save(ownItem);
        return ownItem;

    }

    public OwnItem addNewOwnItem(Integer userId, Integer itemId, Integer itemAmount) {
        OwnItem ownItem = this.findOwnItemByUserIdEqualsAndItemIdEquals(userId, itemId);
        if (ownItem == null) {
            ownItem = new OwnItem();
            ownItem.setUserId(userId);
            ownItem.setItemId(itemId);
            ownItem.setItemCount(itemAmount);
            this.addNewOwnItem(ownItem);
        } else {
            ownItem.setItemCount(ownItem.getItemCount() + itemAmount);
            ownItemRepository.save(ownItem);
        }
        return ownItem;
    }

    public OwnItem updateOwnItem(OwnItem updateOwnItem) {
        final OwnItem ownItem = ownItemRepository.getOne(updateOwnItem.getOwnItemId());
        // GlobalConstants.printIfDoDebug("old Card has an Id of : " + n.getCardId());
        ownItem.setOwnItem(updateOwnItem.getUserId(), updateOwnItem.getItemId(), updateOwnItem.getItemCount(),
                new Timestamp(System.currentTimeMillis()));

        ownItemRepository.updateOwnItemStatus(ownItem, updateOwnItem.getOwnItemId());
        // return "Modified Card";
        // Image = Image.replace(' ', '+');
        return ownItem;

    }

    public List<OwnItem> getAllOwnItems() {
        return ownItemRepository.findAll();
    }

    public OwnItem findOwnItemByUserIdEqualsAndItemIdEquals(Integer userId, Integer itemId) {
        return ownItemRepository.findOwnItemByUserIdEqualsAndItemIdEquals(userId, itemId).orElse(null);
    }

    @Override
    public OwnItem repeatOwnItem(OwnItem ownItem, Integer itemCount) {
        ownItem.setItemCount(itemCount);
        ownItemRepository.updateOwnItemStatus(ownItem, ownItem.getOwnItemId());
        return ownItem;
    }

    public List<OwnItem> getAllOwnItemsByUserId(Integer userId) {
        return ownItemRepository.findAllByUserIdEquals(userId);
    }

    public String deleteOwnItems(List<Integer> ownItemIds) {
        for (Integer ownItemId : ownItemIds) {
            ownItemRepository.deleteById(ownItemId);
        }
        return "Deleted OwnItems by id";
    }

    public String deleteAll() {
        ownItemRepository.deleteAll();
        return "Deleted All OwnItems";
    }

    public List<OwnItem> deleteOwnItem(Integer userId, Integer itemId) {
        ownItemRepository.deleteOwnItemByUserIdEqualsAndItemIdEquals(userId, itemId);
        return getAllOwnItems();
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        return this.ListPage(page_token, page_size, ownItemRepository);
    }

}
