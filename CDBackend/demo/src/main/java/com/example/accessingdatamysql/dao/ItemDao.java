package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface ItemDao {

        Item getOneItem(Integer ItemId);

        // Optional<ItemDetails> findOneDetail(Integer id);

        // void modifyStorage(Item Item);

        Item addNewItem(Item newItem);

        Item updateItem(Item updateItem);

        List<Item> getAllItems();

        String deleteItems(List<Integer> ItemIds);

        String deleteAll();

        List<Item> deleteItem(Integer itemId);

}
