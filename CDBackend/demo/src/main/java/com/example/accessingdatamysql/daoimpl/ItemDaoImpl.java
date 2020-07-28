package com.example.accessingdatamysql.daoimpl;

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
    private ItemRepository ItemRepository;
    @Autowired
    private ItemDetailsRepository ItemDetailsRepository;

    @Override
    public Item getOneItem(Integer ItemId) {
        Item Item = ItemRepository.getOne(ItemId);
        Optional<ItemDetails> ItemDetails = ItemDetailsRepository.findItemDetailsByItemIdEquals(ItemId);
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
        // System.out.println("new Item has an Id of : " + n.getItemId());
        ItemRepository.save(Item);
        ItemDetails ItemDetails = new ItemDetails(Item.getItemId(), newItem.getItemDetails().getItemImg(),
                newItem.getItemDetails().getItemDescription());
        ItemDetailsRepository.save(ItemDetails);
        Item.setItemDetails(ItemDetails);
        return Item;

    }

    public Item updateItem(Item updateItem) {
        System.out.println(updateItem);
        Item Item = ItemRepository.getOne(updateItem.getItemId());
        // System.out.println("old Item has an Id of : " + n.getItemId());
        Item.setItem(updateItem.getItemName(), updateItem.getPrice());

        ItemRepository.updateItemStatus(Item, updateItem.getItemId());

        Optional<ItemDetails> optItemDetails = ItemDetailsRepository
                .findItemDetailsByItemIdEquals(updateItem.getItemId());
        ItemDetails itemDetails = new ItemDetails(updateItem.getItemId(), "", "");
        System.out.println(itemDetails);
        if (optItemDetails.isPresent()) {
            System.out.println("Item Exists");
            itemDetails = optItemDetails.get();
        } else {
            System.out.println("Item doesn't exist");
        }

        itemDetails.setItemDescription(updateItem.getItemDetails().getItemDescription());
        itemDetails.setItemImg(updateItem.getItemDetails().getItemImg());
        ItemDetailsRepository.save(itemDetails);
        Item.setItemDetails(itemDetails);
        return Item;

    }

    public List<Item> getAllItems() {
        List<Item> Items = ItemRepository.findAll();
        for (int i = 0; i < Items.size(); i++) {
            Item Item = Items.get(i);
            Optional<ItemDetails> ItemDetails = ItemDetailsRepository.findItemDetailsByItemIdEquals(Item.getItemId());
            ItemDetails.ifPresent(Item::setItemDetails);
            Items.set(i, Item);
        }
        return Items;
    }

    public String deleteItems(List<Integer> ItemIds) {
        for (int i = 0; i < ItemIds.size(); i++) {
            ItemRepository.deleteById(ItemIds.get(i));
            ItemDetailsRepository.deleteItemDetailsByItemIdEquals(ItemIds.get(i));
        }
        return "Deleted Items by id";
    }

    public String deleteAll() {
        ItemRepository.deleteAll();
        ItemDetailsRepository.deleteAll();
        return "Deleted All Items";
    }

    public List<Item> deleteItem(Integer itemId) {
        ItemRepository.deleteById(itemId);
        ItemDetailsRepository.deleteItemDetailsByItemIdEquals(itemId);
        return getAllItems();
    };
}
