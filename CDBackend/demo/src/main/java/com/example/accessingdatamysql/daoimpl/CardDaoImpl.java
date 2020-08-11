package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.CardDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.io.Console;
import java.util.*;

@Repository
public class CardDaoImpl implements CardDao {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardDetailsRepository cardDetailsRepository;

    @Override
    public Card getOneCard(Integer cardId) {
        Card card = cardRepository.getOne(cardId);
        Optional<CardDetails> cardDetails = cardDetailsRepository.findCardDetailsByCardIdEquals(cardId);
        cardDetails.ifPresent(card::setCardDetails);
        return card;
    }

    public Card addNewCard(Card newCard) {

        Card card = new Card(newCard.getRarity(), newCard.getCardName(), newCard.getHealthPoint(), newCard.getAttack(),
                newCard.getDefense(), newCard.getAttackRange(), newCard.getCd(), newCard.getSpeed(), newCard.getType());
        // System.out.println("new Card has an Id of : " + n.getCardId());
        cardRepository.save(card);
        CardDetails cardDetails = new CardDetails(card.getCardId(), newCard.getCardDetails().getCardImg(),
                newCard.getCardDetails().getShortDescription(), newCard.getCardDetails().getCardDescription());
        cardDetailsRepository.save(cardDetails);
        card.setCardDetails(cardDetails);
        return card;

    }

    public Card updateCard(Card updateCard) {
        Card card = cardRepository.getOne(updateCard.getCardId());
        // System.out.println("old Card has an Id of : " + n.getCardId());
        card.setCard(updateCard.getRarity(), updateCard.getCardName(), updateCard.getHealthPoint(),
                updateCard.getAttack(), updateCard.getDefense(), updateCard.getAttackRange(), updateCard.getCd(),
                updateCard.getSpeed(), updateCard.getType());

        cardRepository.updateCardStatus(card, updateCard.getCardId());
        Optional<CardDetails> optCardDetails = cardDetailsRepository
                .findCardDetailsByCardIdEquals(updateCard.getCardId());
        CardDetails cardDetails = new CardDetails(updateCard.getCardId(), "", "", "");
        if (optCardDetails.isPresent()) {
            System.out.println("Card Exists");
            cardDetails = optCardDetails.get();
        } else {
            System.out.println("Card doesn't exist");
        }

        cardDetails.setCardDescription(updateCard.getCardDetails().getCardDescription());
        cardDetails.setShortDescription(updateCard.getCardDetails().getShortDescription());
        cardDetails.setCardImg(updateCard.getCardDetails().getCardImg());
        cardDetailsRepository.save(cardDetails);

        card.setCardDetails(cardDetails);

        return card;
    }

    public List<Card> getAllCards() {
        List<Card> Cards = cardRepository.findAll();
        for (int i = 0; i < Cards.size(); i++) {
            Card card = Cards.get(i);
            Optional<CardDetails> CardDetails = cardDetailsRepository.findCardDetailsByCardIdEquals(card.getCardId());
            CardDetails.ifPresent(card::setCardDetails);
            Cards.set(i, card);
        }
        return Cards;
    }

    public String deleteCards(List<Integer> cardIds) {
        for (int i = 0; i < cardIds.size(); i++) {
            cardRepository.deleteById(cardIds.get(i));
            cardDetailsRepository.deleteCardDetailsByCardIdEquals(cardIds.get(i));
        }
        return "Deleted Cards by id";
    }

    public String deleteAll() {
        cardRepository.deleteAll();
        cardDetailsRepository.deleteAll();
        return "Deleted All Cards";
    }

    public List<Card> deleteCard(Integer cardId) {
        cardRepository.deleteById(cardId);
        cardDetailsRepository.deleteCardDetailsByCardIdEquals(cardId);
        return getAllCards();
    }

    @Override
    public List<Card> getByRarityAndType(String rarity, Integer type) {
        return cardRepository.getByRarityAndType(rarity, type);
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        return this.ListPage(page_token, page_token, cardRepository, card -> {
            cardDetailsRepository
                    .findCardDetailsByCardIdEquals(card.getCardId())
                    .ifPresent(card::setCardDetails);
            return card;
        });
    }

}
