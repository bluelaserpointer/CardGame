package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.OwnCardDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class OwnCardDaoImpl implements OwnCardDao {
    @Autowired
    private OwnCardRepository OwnCardRepository;

    // 用ownCardId找某一个用户拥有卡牌的关系
    @Override
    public OwnCard getOneOwnCard(Integer OwnCardId) {
        OwnCard ownCard = OwnCardRepository.getOne(OwnCardId);
        return ownCard;
    }

    // 增加一个用户拥有某张卡牌的关系
    public OwnCard addNewOwnCard(Integer userId, Integer cardId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // 获取当前时间作为获取卡牌的时间
        OwnCard owncard = new OwnCard(userId, cardId, timestamp);
        OwnCardRepository.save(owncard);
        return owncard;

    }

    // 更新一个用户拥有某张卡牌的所有信息
    public OwnCard updateOwnCard(OwnCard updateOwnCard) {
        Optional<OwnCard> optOwnCard = OwnCardRepository
                .findOwnCardByUserIdEqualsAndCardIdEquals(updateOwnCard.getUserId(), updateOwnCard.getCardId());
        if (optOwnCard.isPresent()) {
            OwnCardRepository.updateOwnCardStatus(updateOwnCard, updateOwnCard.getOwnCardId());
            return updateOwnCard;
        }
        return null;
    }

    // 用户拥有的某张卡牌升级
    public OwnCard cardLevelUp(Integer userId, Integer cardId) {
        Optional<OwnCard> optOwnCard = OwnCardRepository.findOwnCardByUserIdEqualsAndCardIdEquals(userId, cardId);
        if (optOwnCard.isPresent()) {
            OwnCard ownCard = optOwnCard.get();
            ownCard.setCardLevel(ownCard.getCardLevel() + 1);
            ownCard.setCardCurExp(0);
            OwnCardRepository.updateOwnCardStatus(ownCard, ownCard.getOwnCardId());
            return ownCard;
        }
        return null;
    }

    // 用户再一次拥有已经拥有的卡牌
    public OwnCard ownAnotherCard(OwnCard ownCard) {
        ownCard.setRepetitiveOwns(ownCard.getRepetitiveOwns() + 1);
        // 一开始是50级，然后每提升卡牌张数+1
        ownCard.setCardLevelLimit(ownCard.getCardLevelLimit() + 1);
        OwnCardRepository.updateOwnCardStatus(ownCard, ownCard.getOwnCardId());
        return ownCard;
    }

    public OwnCard findOwnCardByUserIdEqualsAndCardIdEquals(Integer userId, Integer cardId) {
        Optional<OwnCard> optOwnCard = OwnCardRepository.findOwnCardByUserIdEqualsAndCardIdEquals(userId, cardId);
        if (optOwnCard.isPresent()) {
            return optOwnCard.get();
        }
        return null;
    }

    // 获取所有的用户拥有卡牌记录
    public List<OwnCard> getAllOwnCards() {
        List<OwnCard> OwnCards = OwnCardRepository.findAll();
        return OwnCards;
    }

    // 获取指定用户的所有拥有卡牌记录
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

    // 用ownCardIds来删除拥有卡牌关系
    public String deleteOwnCards(List<Integer> OwncardIds) {
        for (int i = 0; i < OwncardIds.size(); i++) {
            OwnCardRepository.deleteById(OwncardIds.get(i));
        }
        return "Deleted OwnCards by id";
    }

    // 删除所有拥有卡牌关系
    public String deleteAll() {
        OwnCardRepository.deleteAll();
        return "Deleted All OwnCards";
    }

    // 删除单个拥有卡牌关系
    public List<OwnCard> deleteOwnCard(Integer userId, Integer cardId) {
        OwnCardRepository.deleteOwnCardByUserIdEqualsAndCardIdEquals(userId, cardId);
        return getAllOwnCards();
    }

    // 计算更新剩余点数有多少
    @Override
    public Integer CaluculateLeftPoints(OwnCard ownCard) {
        ownCard.setLeftPoints(ownCard.getEnhancePoint() - ownCard.getEnhanceAttack() - ownCard.getEnhanceAttackRange()
                - ownCard.getEnhanceCD() - ownCard.getEnhanceDefense() - ownCard.getEnhanceHP()
                - ownCard.getEnhanceSpeed());
        return ownCard.getLeftPoints();
    }

}
