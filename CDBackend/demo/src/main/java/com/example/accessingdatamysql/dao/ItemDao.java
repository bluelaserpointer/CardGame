package com.example.accessingdatamysql.dao;

import java.util.List;

import com.example.accessingdatamysql.Classes.PaginationDao;
import com.example.accessingdatamysql.entity.*;

public interface ItemDao extends PaginationDao {

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
