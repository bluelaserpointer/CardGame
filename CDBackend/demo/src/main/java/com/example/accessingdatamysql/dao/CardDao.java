package com.example.accessingdatamysql.dao;

import java.util.List;
import com.example.accessingdatamysql.Classes.JSONPagination;
import com.example.accessingdatamysql.entity.*;

public interface CardDao extends JSONPagination {
        Card getOneCard(Integer cardId);

        // Optional<CardDetails> findOneDetail(Integer id);

        // void modifyStorage(Card Card);

        Card addNewCard(Card newCard);

        Card updateCard(Card updateCard);

        List<Card> getAllCards();

        String deleteCards(List<Integer> cardIds);

        String deleteAll();

        List<Card> deleteCard(Integer cardId);

        List<Card> getByRarityAndType(String rarity, Integer type);

}
