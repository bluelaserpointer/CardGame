package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.*;

public interface CardDao {
        Card getOneCard(Integer cardId);

        // Optional<CardDetails> findOneDetail(Integer id);

        // void modifyStorage(Card Card);

        Card addNewCard(Card newCard);

        Card updateCard(Card updateCard);

        JSONObject ListPage(Integer page_token, Integer page_size);

        List<Card> getAllCards();

        String deleteCards(List<Integer> cardIds);

        String deleteAll();

        List<Card> deleteCard(Integer cardId);

}
