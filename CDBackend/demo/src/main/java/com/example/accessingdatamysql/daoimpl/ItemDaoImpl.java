package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.GlobalConstants;
import com.example.accessingdatamysql.dao.ItemDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.io.Console;
import java.util.*;

@Repository
public class ItemDaoImpl implements ItemDao {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemDetailsRepository itemDetailsRepository;

    @Override
    public Item getOneItem(Integer ItemId) {
        Item Item = itemRepository.getOne(ItemId);
        Optional<ItemDetails> ItemDetails = itemDetailsRepository.findItemDetailsByItemIdEquals(ItemId);
        ItemDetails.ifPresent(Item::setItemDetails);
        return Item;
    }

    // public void modifyStorage(Item Item) {
    // ItemRepository.updateStorageStatus(Item.getStorage(), Item.getItemId());
    // return;
    // }

    // public ItemDetails findOneDetail(Integer id) {
    // return ItemDetailsRepository.findById(id);
    // }

    public Item addNewItem(Item newItem) {

        Item Item = new Item(newItem.getItemName(), newItem.getPrice());
        // GlobalConstants.printIfDoDebug("new Item has an Id of : " + n.getItemId());
        itemRepository.save(Item);
        ItemDetails ItemDetails = new ItemDetails(Item.getItemId(), newItem.getItemDetails().getItemImg(),
                newItem.getItemDetails().getItemDescription());
        itemDetailsRepository.save(ItemDetails);
        Item.setItemDetails(ItemDetails);
        return Item;

    }

    public Item updateItem(Item updateItem) {
        GlobalConstants.printIfDoDebug(updateItem);
        Item Item = itemRepository.getOne(updateItem.getItemId());
        // GlobalConstants.printIfDoDebug("old Item has an Id of : " + n.getItemId());
        Item.setItem(updateItem.getItemName(), updateItem.getPrice());

        itemRepository.updateItemStatus(Item, updateItem.getItemId());

        Optional<ItemDetails> optItemDetails = itemDetailsRepository
                .findItemDetailsByItemIdEquals(updateItem.getItemId());
        ItemDetails itemDetails = new ItemDetails(updateItem.getItemId(), "", "");
        GlobalConstants.printIfDoDebug(itemDetails);
        if (optItemDetails.isPresent()) {
            GlobalConstants.printIfDoDebug("Item Exists");
            itemDetails = optItemDetails.get();
        } else {
            GlobalConstants.printIfDoDebug("Item doesn't exist");
        }

        itemDetails.setItemDescription(updateItem.getItemDetails().getItemDescription());
        itemDetails.setItemImg(updateItem.getItemDetails().getItemImg());
        itemDetailsRepository.save(itemDetails);
        Item.setItemDetails(itemDetails);
        return Item;

    }

    public List<Item> getAllItems() {
        List<Item> Items = itemRepository.findAll();
        for (int i = 0; i < Items.size(); i++) {
            Item Item = Items.get(i);
            Optional<ItemDetails> ItemDetails = itemDetailsRepository.findItemDetailsByItemIdEquals(Item.getItemId());
            ItemDetails.ifPresent(Item::setItemDetails);
            Items.set(i, Item);
        }
        return Items;
    }

    public String deleteItems(List<Integer> ItemIds) {
        for (int i = 0; i < ItemIds.size(); i++) {
            itemRepository.deleteById(ItemIds.get(i));
            itemDetailsRepository.deleteItemDetailsByItemIdEquals(ItemIds.get(i));
        }
        return "Deleted Items by id";
    }

    public String deleteAll() {
        itemRepository.deleteAll();
        itemDetailsRepository.deleteAll();
        return "Deleted All Items";
    }

    public List<Item> deleteItem(Integer itemId) {
        itemRepository.deleteById(itemId);
        itemDetailsRepository.deleteItemDetailsByItemIdEquals(itemId);
        return getAllItems();
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        return this.ListPage(page_token, page_size, itemRepository, item -> {
            itemDetailsRepository
                    .findItemDetailsByItemIdEquals(item.getItemId())
                    .ifPresent(item::setItemDetails);
            return item;
        });
    }
}
