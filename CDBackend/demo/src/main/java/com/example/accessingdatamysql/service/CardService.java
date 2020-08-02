package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Card;
import com.example.accessingdatamysql.entity.ListRequest;

import java.util.List;

public interface CardService {
        Card getOneCard(Integer cardId);

        Card addNewCard(Card newCard);

        Card updateCard(Card updateCard);

        // 获取指定页数的Activity
        List<Card> ListPage(ListRequest listRequest);

        List<Card> getAllCards();

        String deleteCards(List<Integer> cardIds);

        String deleteAll();

        List<Card> deleteCard(Integer cardId);
}
