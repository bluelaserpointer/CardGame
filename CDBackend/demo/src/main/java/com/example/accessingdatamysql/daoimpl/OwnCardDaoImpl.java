package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.GlobalConstants;
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
    private OwnCardRepository ownCardRepository;

    // 用ownCardId找某一个用户拥有卡牌的关系
    @Override
    public OwnCard getOneOwnCard(Integer OwnCardId) {
        return ownCardRepository.getOne(OwnCardId);
    }

    // 增加一个用户拥有某张卡牌的关系
    public OwnCard addNewOwnCard(Integer userId, Integer cardId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // 获取当前时间作为获取卡牌的时间
        OwnCard owncard = new OwnCard(userId, cardId, timestamp);
        ownCardRepository.save(owncard);
        return owncard;

    }

    // 更新一个用户拥有某张卡牌的所有信息
    public OwnCard updateOwnCard(OwnCard updateOwnCard) {
        GlobalConstants.printIfDoDebug(updateOwnCard);
        Optional<OwnCard> optOwnCard = ownCardRepository
                .findOwnCardByUserIdEqualsAndCardIdEquals(updateOwnCard.getUserId(), updateOwnCard.getCardId());
        if (optOwnCard.isPresent()) {
            ownCardRepository.updateOwnCardStatus(updateOwnCard, updateOwnCard.getOwnCardId());
            return updateOwnCard;
        }
        GlobalConstants.printIfDoDebug("Not In isPresent");
        return null;
    }

    @Override
    public OwnCard redistributeUpgrades(OwnCard updateOwnCard) {
        final Optional<OwnCard> optOwnCard = ownCardRepository.findById(updateOwnCard.getOwnCardId());
        if (optOwnCard.isPresent()) {
            final OwnCard originalOwnCard = optOwnCard.get();
            int leftUpgPt = originalOwnCard.getLeftPoints();
            Integer newEnhancedPt;
            if ((newEnhancedPt = updateOwnCard.getEnhanceAttack()) != null) {
                leftUpgPt -= (newEnhancedPt - originalOwnCard.getEnhanceAttack());
                originalOwnCard.setEnhanceAttack(newEnhancedPt);
            }
            if ((newEnhancedPt = updateOwnCard.getEnhanceAttackRange()) != null) {
                leftUpgPt -= (newEnhancedPt - originalOwnCard.getEnhanceAttackRange());
                originalOwnCard.setEnhanceAttackRange(newEnhancedPt);
            }
            if ((newEnhancedPt = updateOwnCard.getEnhanceCD()) != null) {
                leftUpgPt -= (newEnhancedPt - originalOwnCard.getEnhanceCD());
                originalOwnCard.setEnhanceCD(newEnhancedPt);
            }
            if ((newEnhancedPt = updateOwnCard.getEnhanceDefense()) != null) {
                leftUpgPt -= (newEnhancedPt - originalOwnCard.getEnhanceDefense());
                originalOwnCard.setEnhanceDefense(newEnhancedPt);
            }
            if ((newEnhancedPt = updateOwnCard.getEnhanceHP()) != null) {
                leftUpgPt -= (newEnhancedPt - originalOwnCard.getEnhanceHP());
                originalOwnCard.setEnhanceHP(newEnhancedPt);
            }
            if ((newEnhancedPt = updateOwnCard.getEnhanceSpeed()) != null) {
                leftUpgPt -= (newEnhancedPt - originalOwnCard.getEnhanceSpeed());
                originalOwnCard.setEnhanceSpeed(newEnhancedPt);
            }
            if (leftUpgPt < 0) {
                GlobalConstants.printIfDoDebug(
                        "OwnCardDaoImpl::redistributeUpgrades: Illegal redistribute! Left point: " + leftUpgPt);
                return null;
            }
            originalOwnCard.setLeftPoints(leftUpgPt);
            ownCardRepository.updateOwnCardStatus(updateOwnCard, updateOwnCard.getOwnCardId());
            return updateOwnCard;
        }
        GlobalConstants.printIfDoDebug("OwnCardDaoImpl::redistributeUpgrades: Not found id: " + updateOwnCard.getOwnCardId());
        return null;
    }

    // 用户拥有的某张卡牌升级
    public OwnCard cardLevelUp(Integer userId, Integer cardId) {
        Optional<OwnCard> optOwnCard = ownCardRepository.findOwnCardByUserIdEqualsAndCardIdEquals(userId, cardId);
        if (optOwnCard.isPresent()) {
            OwnCard ownCard = optOwnCard.get();
            ownCard.setCardLevel(ownCard.getCardLevel() + 1);
            ownCard.setCardCurExp(0);
            ownCardRepository.updateOwnCardStatus(ownCard, ownCard.getOwnCardId());
            return ownCard;
        }
        return null;
    }

    // 用户再一次拥有已经拥有的卡牌
    public OwnCard ownAnotherCard(OwnCard ownCard) {
        ownCard.setRepetitiveOwns(ownCard.getRepetitiveOwns() + 1);
        // 一开始是50级，然后每提升卡牌张数+1
        ownCard.setCardLevelLimit(ownCard.getCardLevelLimit() + 1);
        ownCardRepository.updateOwnCardStatus(ownCard, ownCard.getOwnCardId());
        return ownCard;
    }

    public OwnCard findOwnCardByUserIdEqualsAndCardIdEquals(Integer userId, Integer cardId) {
        Optional<OwnCard> optOwnCard = ownCardRepository.findOwnCardByUserIdEqualsAndCardIdEquals(userId, cardId);
        return optOwnCard.orElse(null);
    }

    // 获取所有的用户拥有卡牌记录
    public List<OwnCard> getAllOwnCards() {
        return ownCardRepository.findAll();
    }

    // 获取指定用户的所有拥有卡牌记录
    public List<OwnCard> getAllOwnCardsByUserId(Integer userId) {
        return ownCardRepository.findAllByUserIdEquals(userId);
    }

    // 用ownCardIds来删除拥有卡牌关系
    public String deleteOwnCards(List<Integer> ownCardIds) {
        for (Integer ownCardId : ownCardIds) {
            ownCardRepository.deleteById(ownCardId);
        }
        return "Deleted OwnCards by id";
    }

    // 删除所有拥有卡牌关系
    public String deleteAll() {
        ownCardRepository.deleteAll();
        return "Deleted All OwnCards";
    }

    // 删除单个拥有卡牌关系
    public List<OwnCard> deleteOwnCard(Integer userId, Integer cardId) {
        ownCardRepository.deleteOwnCardByUserIdEqualsAndCardIdEquals(userId, cardId);
        return getAllOwnCards();
    }

    // 计算更新剩余点数有多少
    @Override
    public Integer calculateLeftPoints(OwnCard ownCard) {
        ownCard.setLeftPoints(ownCard.getEnhancePoint() - ownCard.getEnhanceAttack() - ownCard.getEnhanceAttackRange()
                - ownCard.getEnhanceCD() - ownCard.getEnhanceDefense() - ownCard.getEnhanceHP()
                - ownCard.getEnhanceSpeed());
        return ownCard.getLeftPoints();
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        return this.ListPage(page_token, page_size, ownCardRepository);
    }

}
