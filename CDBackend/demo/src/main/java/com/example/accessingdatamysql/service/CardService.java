package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Activity;
import com.example.accessingdatamysql.entity.Card;

import java.util.List;

public interface CardService {
        Card getOneCard(Integer cardId);

        String addNewCard(String cardName, String rarity, Integer healthPoint, Integer attack, Integer defense,
                          Integer attackRange, Double cd, Integer speed, String cardImg, String shortDescription,
                          String cardDescription);

        String updateCard(Integer cardId, String cardName, String rarity, Integer healthPoint, Integer attack,
                          Integer defense, Integer attackRange, Double cd, Integer speed, String cardImg,
                          String shortDescription, String cardDescription);

        List<Card> getAllCards();

        String deleteCards(List<Integer> cardIds);

        String deleteAll();

        List<Card> deleteCard(Integer cardId);
}
