package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface CardDao {
        Card getOneCard(Integer cardId);

        // Optional<CardDetails> findOneDetail(Integer id);

        // void modifyStorage(Card Card);

        Card addNewCard(Card newCard);

        Card updateCard(Card updateCard);

        List<Card> getAllCards();

        String deleteCards(List<Integer> cardIds);

        String deleteAll();

        List<Card> deleteCard(Integer cardId);

}
