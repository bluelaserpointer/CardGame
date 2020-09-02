package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.GlobalConstants;
import com.example.accessingdatamysql.entity.Card;
import com.example.accessingdatamysql.entity.User;
import com.example.accessingdatamysql.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.accessingdatamysql.Classes.*;

import java.util.List;

@Service
public class MechanismServiceImpl implements MechanismService {
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private OwnCardService ownCardService;

    @Override
    public Integer drawCard(Integer userId, Integer chi, Integer mat, Integer eng) {
        final User user = userService.getOneUser(userId);
        // LotteSpring(subject rarity)
        GlobalConstants.printIfDoDebug("MechanismServiceImpl: " + user.getUserName() + "requested card draw with resources chi: " + chi + " mat: " + mat + " eng: " + eng);

        //resource check
        if(user.getChiKnowledge() < chi) {
            GlobalConstants.printIfDoDebug("!!!MechanismServiceImpl: Illegal left resource of chi " + user.getChiKnowledge() + " with requested " + chi);
            return -1;
        }if(user.getMathKnowledge() < mat) {
            GlobalConstants.printIfDoDebug("!!!MechanismServiceImpl: Illegal left resource of mat " + user.getMathKnowledge() + " with requested " + mat);
            return -1;
        }if(user.getEngKnowledge() < eng) {
            GlobalConstants.printIfDoDebug("!!!MechanismServiceImpl: Illegal left resource of eng " + user.getEngKnowledge() + " with requested " + eng);
            return -1;
        }
        //calculate rarity and type
        final int SUM = chi + mat + eng;
        final int RAND = (int) (Math.random() * 1000.0);
        int rarity;
        if (SUM <= 5) {
            if (RAND < 900 - 10 * SUM)
                rarity = 0;
            else
                rarity = 1;
        } else if (SUM <= 20) {
            if (RAND < 700 - 10 * SUM)
                rarity = 0;
            else if (RAND < 900 - 3 * SUM)
                rarity = 1;
            else
                rarity = 2;
        } else if (SUM <= 50) {
            if (RAND < 600 - 10 * SUM)
                rarity = 0;
            else if (RAND < 800 - 4 * SUM)
                rarity = 1;
            else if (RAND < 950 - 1 * SUM)
                rarity = 2;
            else
                rarity = 3;
        } else if (SUM <= 100) {
            if (RAND < 700 - 6 * SUM)
                rarity = 1;
            else if (RAND < 1500 - 10 * SUM)
                rarity = 2;
            else if (RAND < 1150 - 3 * SUM)
                rarity = 3;
            else
                rarity = 4;
        } else {
            rarity = 4;
        }
        final int RAND2 = (int) (Math.random() * SUM);
        int subject;
        if (RAND2 < chi)
            subject = 0;
        else if (RAND2 < chi + mat)
            subject = 1;
        else
            subject = 2;
        //choose random one in same rarity and same type
        final String rarityStr = Rarity.values()[rarity].toString();
        final List<Card> cardSpring = cardService.getByRarityAndType(rarityStr , subject);
        GlobalConstants.printIfDoDebug("MechanismServiceImpl: search for " + rarityStr + " in " + subject + ": found " + cardSpring.size());
        final Card drawnCard;
        if(cardSpring.isEmpty()) { //Ouch...I guess the rarity does not exist in that subject.
            GlobalConstants.printIfDoDebug("!!!MechanismServiceImpl: Could not found any card. Trying returning random card.");
            drawnCard = cardService.getAllCards().get(0);
        } else {
            drawnCard = cardSpring.get((int) (Math.random() * cardSpring.size()));
        }
        GlobalConstants.printIfDoDebug("MechanismServiceImpl: drawnCard is " + drawnCard.getCardName());
        //consume resources
        user.setChiKnowledge(user.getChiKnowledge() - chi);
        user.setMathKnowledge(user.getMathKnowledge() - mat);
        user.setEngKnowledge(user.getEngKnowledge() - eng);
        //add card
        ownCardService.addNewOwnCard(user.getUserId(), drawnCard.getCardId());
        return drawnCard.getCardId();
    }
}
