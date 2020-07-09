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

    public String addNewItem(String itemName, Integer price, String itemImg, String itemDescription) {

        Item Item = new Item(itemName, price);
        // System.out.println("new Item has an Id of : " + n.getItemId());
        ItemRepository.save(Item);
        ItemDetails ItemDetails = new ItemDetails(Item.getItemId(), itemImg, itemDescription);
        ItemDetailsRepository.save(ItemDetails);
        return "Saved Item";

    }

    public String updateItem(Integer ItemId, String itemName, Integer price, String itemImg, String itemDescription) {

        Item Item = ItemRepository.getOne(ItemId);
        // System.out.println("old Item has an Id of : " + n.getItemId());
        Item.setItem(itemName, price);

        ItemRepository.updateItemStatus(Item, ItemId);

        Optional<ItemDetails> optItemDetails = ItemDetailsRepository.findItemDetailsByItemIdEquals(ItemId);
        ItemDetails itemDetails = new ItemDetails(ItemId, "", "");
        if (optItemDetails.isPresent()) {
            System.out.println("Item Exists");
            itemDetails = optItemDetails.get();
        } else {
            System.out.println("Item doesn't exist");
        }

        itemDetails.setItemDescription(itemDescription);
        itemDetails.setItemImg(itemImg);
        ItemDetailsRepository.save(itemDetails);
        return "modified Item: " + Item.getItemName();

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

    public List<Item> deleteItem(Integer itemId)
    {
      ItemRepository.deleteById(itemId);
      ItemDetailsRepository.deleteItemDetailsByItemIdEquals(itemId);
      return getAllItems();
    };
}
