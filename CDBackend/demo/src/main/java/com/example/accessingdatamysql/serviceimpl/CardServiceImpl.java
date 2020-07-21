package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardDao CardDao;

    @Override
    public Card getOneCard(Integer CardId) {
        return CardDao.getOneCard(CardId);
    }

    public String addNewCard(String cardName, String rarity, Integer healthPoint, Integer attack, Integer defense,
            Integer attackRange, Double cd, Integer speed, String cardImg, String shortDescription,
            String cardDescription, Integer type) {
        return CardDao.addNewCard(cardName, rarity, healthPoint, attack, defense, attackRange, cd, speed, cardImg,
                shortDescription, cardDescription, type);
    }

    public String updateCard(Integer cardId, String cardName, String rarity, Integer healthPoint, Integer attack,
            Integer defense, Integer attackRange, Double cd, Integer speed, String cardImg, String shortDescription,
            String cardDescription, Integer type) {
        System.out.println("In service");
        return CardDao.updateCard(cardId, cardName, rarity, healthPoint, attack, defense, attackRange, cd, speed,
                cardImg, shortDescription, cardDescription, type);
    }

    public List<Card> getAllCards() {
        return CardDao.getAllCards();
    }

    public String deleteCards(List<Integer> CardIds) {
        return CardDao.deleteCards(CardIds);
    }

    public String deleteAll() {
        return CardDao.deleteAll();
    }

    public List<Card> deleteCard(Integer cardId) {
        return CardDao.deleteCard(cardId);
    }
}
