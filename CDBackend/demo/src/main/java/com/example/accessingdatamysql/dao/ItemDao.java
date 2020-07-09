package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface ItemDao {
        Item getOneItem(Integer ItemId);

        // Optional<ItemDetails> findOneDetail(Integer id);

        // void modifyStorage(Item Item);

        String addNewItem(String itemName, Integer price, String itemImg, String itemDescription);

        String updateItem(Integer ItemId, String itemName, Integer price, String itemImg, String itemDescription);

        List<Item> getAllItems();

        String deleteItems(List<Integer> ItemIds);

        String deleteAll();

        List<Item> deleteItem(Integer itemId);

}
