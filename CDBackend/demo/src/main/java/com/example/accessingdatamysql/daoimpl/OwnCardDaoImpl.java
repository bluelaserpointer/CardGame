package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.OwnCardDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
// import java.io.Console;
import java.util.*;

@Repository
public class OwnCardDaoImpl implements OwnCardDao {
    @Autowired
    private OwnCardRepository OwnCardRepository;

    @Override
    public OwnCard getOneOwnCard(Integer OwncardId) {
        OwnCard Owncard = OwnCardRepository.getOne(OwncardId);
        return Owncard;
    }

    public String addNewOwnCard(Integer userId, Integer cardId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        OwnCard owncard = new OwnCard(userId, cardId, timestamp);
        // System.out.println("new OwnCard has an Id of : " + n.getOwnCardId());
        OwnCardRepository.save(owncard);
        return "Saved OwnCard";

    }

    public List<OwnCard> getAllOwnCards() {
        List<OwnCard> OwnCards = OwnCardRepository.findAll();
        return OwnCards;
    }

    public List<OwnCard> getAllOwnCardsByUserId(Integer userId) {
        List<OwnCard> OwnCards = getAllOwnCards();
        List<OwnCard> UserOwnCards = new ArrayList<OwnCard>();
        for (int i = 0; i < OwnCards.size(); i++) {
            OwnCard OwnCard = OwnCards.get(i);
            if (OwnCard.getUserId().equals(userId)) {
                UserOwnCards.add(OwnCard);
            }
        }
        return UserOwnCards;
    }

    public String deleteOwnCards(List<Integer> OwncardIds) {
        for (int i = 0; i < OwncardIds.size(); i++) {
            OwnCardRepository.deleteById(OwncardIds.get(i));
        }
        return "Deleted OwnCards by id";
    }

    public String deleteAll() {
        OwnCardRepository.deleteAll();
        return "Deleted All OwnCards";
    }

    public List<OwnCard> deleteOwnCard(Integer userId, Integer cardId)
    {
        OwnCardRepository.deleteOwnItemByUserIdEqualsAndCardIdEquals(userId, cardId);
        return getAllOwnCards();
    }
}
