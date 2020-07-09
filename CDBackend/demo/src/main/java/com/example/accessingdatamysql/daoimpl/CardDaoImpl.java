package com.example.accessingdatamysql.daoimpl;

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
    private CardRepository CardRepository;
    @Autowired
    private CardDetailsRepository CardDetailsRepository;

    @Override
    public Card getOneCard(Integer cardId) {
        Card card = CardRepository.getOne(cardId);
        Optional<CardDetails> cardDetails = CardDetailsRepository.findCardDetailsByCardIdEquals(cardId);
        cardDetails.ifPresent(card::setCardDetails);
        return card;
    }

    // public void modifyStorage(Card Card) {
    // CardRepository.updateStorageStatus(Card.getStorage(), Card.getCardId());
    // return;
    // }

    // public CardDetails findOneDetail(Integer id) {
    // return CardDetailsRepository.findById(id);
    // }

    public String addNewCard(String cardName, String rarity, Integer healthPoint, Integer attack, Integer defense,
            Integer attackRange, Double cd, Integer speed, String cardImg, String shortDescription,
            String cardDescription) {

        Card card = new Card(rarity, cardName, healthPoint, attack, defense, attackRange, cd, speed);
        // System.out.println("new Card has an Id of : " + n.getCardId());
        CardRepository.save(card);
        CardDetails cardDetails = new CardDetails(card.getCardId(), cardImg, shortDescription, cardDescription);
        CardDetailsRepository.save(cardDetails);
        return "Saved Card";

    }

    public String updateCard(Integer cardId, String cardName, String rarity, Integer healthPoint, Integer attack,
            Integer defense, Integer attackRange, Double cd, Integer speed, String cardImg, String shortDescription,
            String cardDescription) {
        Card card = CardRepository.getOne(cardId);
        // System.out.println("old Card has an Id of : " + n.getCardId());
        card.setCard(rarity, cardName, healthPoint, attack, defense, attackRange, cd, speed);

        CardRepository.updateCardStatus(card, cardId);
        Optional<CardDetails> optCardDetails = CardDetailsRepository.findCardDetailsByCardIdEquals(cardId);
        CardDetails cardDetails = new CardDetails(cardId, "", "", "");
        if (optCardDetails.isPresent()) {
            System.out.println("Card Exists");
            cardDetails = optCardDetails.get();
        } else {
            System.out.println("Card doesn't exist");
        }

        cardDetails.setCardDescription(cardDescription);
        cardDetails.setShortDescription(shortDescription);
        cardDetails.setCardImg(cardImg);
        CardDetailsRepository.save(cardDetails);

        return "modified card: " + card.getCardName();
    }

    public List<Card> getAllCards() {
        List<Card> Cards = CardRepository.findAll();
        for (int i = 0; i < Cards.size(); i++) {
            Card card = Cards.get(i);
            Optional<CardDetails> CardDetails = CardDetailsRepository.findCardDetailsByCardIdEquals(card.getCardId());
            CardDetails.ifPresent(card::setCardDetails);
            Cards.set(i, card);
        }
        return Cards;
    }

    public String deleteCards(List<Integer> cardIds) {
        for (int i = 0; i < cardIds.size(); i++) {
            CardRepository.deleteById(cardIds.get(i));
            CardDetailsRepository.deleteCardDetailsByCardIdEquals(cardIds.get(i));
        }
        return "Deleted Cards by id";
    }

    public String deleteAll() {
        CardRepository.deleteAll();
        CardDetailsRepository.deleteAll();
        return "Deleted All Cards";
    }

    public List<Card> deleteCard(Integer cardId) {
        CardRepository.deleteById(cardId);
        CardDetailsRepository.deleteCardDetailsByCardIdEquals(cardId);
        return getAllCards();
    }

}
