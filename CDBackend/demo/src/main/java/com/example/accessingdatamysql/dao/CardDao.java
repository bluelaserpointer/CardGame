package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface CardDao {
        Card getOneCard(Integer cardId);

        // Optional<CardDetails> findOneDetail(Integer id);

        // void modifyStorage(Card Card);

        String addNewCard(String cardName, String rarity, Integer healthPoint, Integer attack, Integer defense,
                        Integer attackRange, Double cd, Integer speed, String cardImg, String shortDescription,
                        String cardDescription, Integer type);

        String updateCard(Integer cardId, String cardName, String rarity, Integer healthPoint, Integer attack,
                        Integer defense, Integer attackRange, Double cd, Integer speed, String cardImg,
                        String shortDescription, String cardDescription, Integer type);

        List<Card> getAllCards();

        String deleteCards(List<Integer> cardIds);

        String deleteAll();

        List<Card> deleteCard(Integer cardId);

}
