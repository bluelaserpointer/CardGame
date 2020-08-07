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

    public Card addNewCard(Card newCard) {

        Card card = new Card(newCard.getRarity(), newCard.getCardName(), newCard.getHealthPoint(), newCard.getAttack(),
                newCard.getDefense(), newCard.getAttackRange(), newCard.getCd(), newCard.getSpeed(), newCard.getType());
        // System.out.println("new Card has an Id of : " + n.getCardId());
        CardRepository.save(card);
        CardDetails cardDetails = new CardDetails(card.getCardId(), newCard.getCardDetails().getCardImg(),
                newCard.getCardDetails().getShortDescription(), newCard.getCardDetails().getCardDescription());
        CardDetailsRepository.save(cardDetails);
        card.setCardDetails(cardDetails);
        return card;

    }

    public Card updateCard(Card updateCard) {
        Card card = CardRepository.getOne(updateCard.getCardId());
        // System.out.println("old Card has an Id of : " + n.getCardId());
        card.setCard(updateCard.getRarity(), updateCard.getCardName(), updateCard.getHealthPoint(),
                updateCard.getAttack(), updateCard.getDefense(), updateCard.getAttackRange(), updateCard.getCd(),
                updateCard.getSpeed(), updateCard.getType());

        CardRepository.updateCardStatus(card, updateCard.getCardId());
        Optional<CardDetails> optCardDetails = CardDetailsRepository
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
        CardDetailsRepository.save(cardDetails);

        card.setCardDetails(cardDetails);

        return card;
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

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {

        JSONObject response = new JSONObject();

        // get the result data
        Integer start = (page_token - 1) * page_size;
        // System.out.print("Start: " + start + " End: " + end);
        List<Card> cards = CardRepository.ListPage(start, page_size);
        System.out.print("Size: " + cards.size());
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            Optional<CardDetails> cardDetails = CardDetailsRepository.findCardDetailsByCardIdEquals(card.getCardId());
            cardDetails.ifPresent(card::setCardDetails);
            cards.set(i, card);
        }

        // get the nextPageToken
        Integer nextPageToken;
        if ((CardRepository.findAll().size() - (page_token * page_size)) <= 0) {
            response.put("nextPageToken", "");
        } else {
            nextPageToken = page_token + 1;
            response.put("nextPageToken", nextPageToken);
        }

        // get the total pages of the result
        Integer totalPages = CardRepository.findAll().size() / page_size;
        if ((totalPages - page_size * totalPages) > 0) {
            totalPages += 1;
        }
        response.put("result", cards);
        response.put("totalPages", totalPages);

        return response;
    }

}
