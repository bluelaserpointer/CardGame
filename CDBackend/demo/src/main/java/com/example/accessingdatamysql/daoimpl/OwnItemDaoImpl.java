package com.example.accessingdatamysql.daoimpl;

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
    private OwnItemRepository OwnItemRepository;

    @Override
    public OwnItem getOneOwnItem(Integer OwnItemId) {
        OwnItem OwnItem = OwnItemRepository.getOne(OwnItemId);
        return OwnItem;
    }

    public OwnItem addNewOwnItem(OwnItem newOwnItem) {

        Timestamp accquireDate = new Timestamp(System.currentTimeMillis());

        OwnItem ownItem = new OwnItem(newOwnItem.getUserId(), newOwnItem.getItemId(), newOwnItem.getItemCount(),
                accquireDate);
        // System.out.println("new OwnItem has an Id of : " + n.getOwnItemId());
        OwnItemRepository.save(ownItem);
        return ownItem;

    }

    public OwnItem updateOwnItem(OwnItem updateOwnItem) {
        Timestamp accquireDate = new Timestamp(System.currentTimeMillis());
        OwnItem OwnItem = OwnItemRepository.getOne(updateOwnItem.getOwnItemId());
        // System.out.println("old Card has an Id of : " + n.getCardId());
        OwnItem.setOwnItem(updateOwnItem.getUserId(), updateOwnItem.getItemId(), updateOwnItem.getItemCount(),
                accquireDate);

        OwnItemRepository.updateOwnItemStatus(OwnItem, updateOwnItem.getOwnItemId());
        // return "Modified Card";
        // Image = Image.replace(' ', '+');
        return OwnItem;

    }

    public List<OwnItem> getAllOwnItems() {
        List<OwnItem> OwnItems = OwnItemRepository.findAll();
        return OwnItems;
    }

    public OwnItem findOwnItemByUserIdEqualsAndItemIdEquals(Integer userId, Integer itemId) {
        Optional<OwnItem> optOwnItem = OwnItemRepository.findOwnItemByUserIdEqualsAndItemIdEquals(userId, itemId);
        if (optOwnItem.isPresent()) {
            return optOwnItem.get();
        }
        return null;
    }

    @Override
    public OwnItem repeatOwnItem(OwnItem ownItem, Integer itemCount) {
        ownItem.setItemCount(itemCount);
        OwnItemRepository.updateOwnItemStatus(ownItem, ownItem.getOwnItemId());
        return ownItem;
    }

    public List<OwnItem> getAllOwnItemsByUserId(Integer userId) {
        List<OwnItem> OwnItems = getAllOwnItems();
        List<OwnItem> UserOwnItems = new ArrayList<OwnItem>();
        for (int i = 0; i < OwnItems.size(); i++) {
            OwnItem OwnItem = OwnItems.get(i);
            if (OwnItem.getUserId().equals(userId)) {
                UserOwnItems.add(OwnItem);
            }
        }
        return UserOwnItems;
    }

    public String deleteOwnItems(List<Integer> OwnItemIds) {
        for (int i = 0; i < OwnItemIds.size(); i++) {
            OwnItemRepository.deleteById(OwnItemIds.get(i));
        }
        return "Deleted OwnItems by id";
    }

    public String deleteAll() {
        OwnItemRepository.deleteAll();
        return "Deleted All OwnItems";
    }

    public List<OwnItem> deleteOwnItem(Integer userId, Integer itemId) {
        OwnItemRepository.deleteOwnItemByUserIdEqualsAndItemIdEquals(userId, itemId);
        return getAllOwnItems();
    }

}
