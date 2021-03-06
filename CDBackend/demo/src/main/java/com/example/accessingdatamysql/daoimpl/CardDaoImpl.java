package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.GlobalConstants;
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
        // GlobalConstants.printIfDoDebug("new Card has an Id of : " + n.getCardId());
        cardRepository.save(card);
        CardDetails cardDetails = new CardDetails(card.getCardId(), newCard.getCardDetails().getCardImg(),
                newCard.getCardDetails().getShortDescription(), newCard.getCardDetails().getCardDescription());
        cardDetailsRepository.save(cardDetails);
        card.setCardDetails(cardDetails);
        return card;

    }

    public Card updateCard(Card updateCard) {
        Card card = cardRepository.getOne(updateCard.getCardId());
        // GlobalConstants.printIfDoDebug("old Card has an Id of : " + n.getCardId());
        card.setCard(updateCard.getRarity(), updateCard.getCardName(), updateCard.getHealthPoint(),
                updateCard.getAttack(), updateCard.getDefense(), updateCard.getAttackRange(), updateCard.getCd(),
                updateCard.getSpeed(), updateCard.getType());

        cardRepository.updateCardStatus(card, updateCard.getCardId());
        Optional<CardDetails> optCardDetails = cardDetailsRepository
                .findCardDetailsByCardIdEquals(updateCard.getCardId());
        CardDetails cardDetails = new CardDetails(updateCard.getCardId(), "", "", "");
        if (optCardDetails.isPresent()) {
            GlobalConstants.printIfDoDebug("Card Exists");
            cardDetails = optCardDetails.get();
        } else {
            GlobalConstants.printIfDoDebug("Card doesn't exist");
        }

        cardDetails.setCardDescription(updateCard.getCardDetails().getCardDescription());
        cardDetails.setShortDescription(updateCard.getCardDetails().getShortDescription());
        cardDetails.setCardImg(updateCard.getCardDetails().getCardImg());
        cardDetailsRepository.save(cardDetails);

        card.setCardDetails(cardDetails);

        return card;
    }

    public List<Card> getAllCards() {
        //TODO: 可能还可以优化，这是每当要查询时才生成HashMap的版本
        final HashMap<Integer, CardDetails> cardIdAndDetails = new HashMap<>();
        for(CardDetails details : cardDetailsRepository.findAll()) {
            cardIdAndDetails.put(details.getCardId(), details);
        }
        final List<Card> cards = cardRepository.findAll();
        cards.forEach(card -> card.setCardDetails(cardIdAndDetails.get(card.getCardId())));
        return cards;
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
        return this.ListPage(page_token, page_size, cardRepository, card -> {
            cardDetailsRepository
                    .findCardDetailsByCardIdEquals(card.getCardId())
                    .ifPresent(card::setCardDetails);
            return card;
        });
    }

}
