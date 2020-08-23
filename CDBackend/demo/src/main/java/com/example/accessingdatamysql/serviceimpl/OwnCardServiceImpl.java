package com.example.accessingdatamysql.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.OwnCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OwnCardServiceImpl implements OwnCardService {
    @Autowired
    private OwnCardDao OwnCardDao;

    // 用ownCardId找某一个用户拥有卡牌的关系
    @Override
    public OwnCard getOneOwnCard(final Integer OwnCardId) {
        return OwnCardDao.getOneOwnCard(OwnCardId);
    }

    // 增加一个用户拥有某张卡牌的关系
    @Override
    public OwnCard addNewOwnCard(final Integer userId, final Integer cardId) {
        if (OwnCardDao.findOwnCardByUserIdEqualsAndCardIdEquals(userId, cardId) != null) {
            // 重复获取
            return OwnCardDao.ownAnotherCard(OwnCardDao.findOwnCardByUserIdEqualsAndCardIdEquals(userId, cardId));
        }
        // 首次获取
        return OwnCardDao.addNewOwnCard(userId, cardId);
    }

    // 获取指定用户的所有拥有卡牌记录
    public List<OwnCard> getAllOwnCardsByUserId(final Integer userId) {
        return OwnCardDao.getAllOwnCardsByUserId(userId);
    }

    // 获取所有的用户拥有卡牌记录
    public List<OwnCard> getAllOwnCards() {
        return OwnCardDao.getAllOwnCards();
    }

    // 用ownCardIds来删除拥有卡牌关系
    public String deleteOwnCards(final List<Integer> OwnCardIds) {
        return OwnCardDao.deleteOwnCards(OwnCardIds);
    }

    // 删除所有拥有卡牌关系
    public String deleteAll() {
        return OwnCardDao.deleteAll();
    }

    // 删除单个拥有卡牌关系
    public List<OwnCard> deleteOwnCard(final Integer userId, final Integer cardId) {
        return OwnCardDao.deleteOwnCard(userId, cardId);
    }

    // 更新一个用户拥有某张卡牌的所有信息
    @Override
    public OwnCard updateOwnCard(final OwnCard updateOwnCard) {
        return OwnCardDao.updateOwnCard(updateOwnCard);
    }

    @Override
    public OwnCard redistributeUpgrades(OwnCard updateOwnCard) {
        return OwnCardDao.redistributeUpgrades(updateOwnCard);
    }

    // 自动判断增加exp是是否需要升级
    @Override
    public OwnCard addExp(final Integer userId, final Integer cardId, final Integer exp) {
        final OwnCard ownCard = OwnCardDao.findOwnCardByUserIdEqualsAndCardIdEquals(userId, cardId);
        final Integer expToNextLevel = expToLevelUp(ownCard.getCardLevel());
        // System.out.println("UserService -> addExp:");
        // System.out.println("Before Modification: ");
        // System.out.println("User: userId = " + userId + " userName = " +
        // user.getUserName() + " level = "
        // + user.getLevel() + " curExpPoint = " + user.getCurExpPoint() + " exp = " +
        // exp);
        // System.out.println("expToNextLevel: " + expToNextLevel);

        Integer newExp = ownCard.getCardCurExp() + exp;
        if (newExp > expToNextLevel) {
            // 需要判断是否到达等级上限了
            if (ownCard.getCardLevel().equals(ownCard.getCardLevelLimit())) {
                newExp = expToNextLevel - 1; // 到达等级上限了就把newexp设置为升级所需的经验值 - 1
            } else {
                newExp -= expToNextLevel;
                ownCard.setCardLevel(ownCard.getCardLevel() + 1); // 要升级
                ownCard.setEnhancePoint(ownCard.getCardLevel() * 5); // 每次升级要增加5个强化点数
                OwnCardDao.calculateLeftPoints(ownCard); // 更新剩余点数
            }
        }
        ownCard.setCardCurExp(newExp);
        updateOwnCard(ownCard);
        // System.out.println("After Modification: ");
        // System.out.println("User: userId = " + userId + " userName = " +
        // user.getUserName() + " level = "
        // + user.getLevel() + " curExpPoint = " + user.getCurExpPoint());

        return ownCard;
    }

    // 计算升级需要的经验值
    @Override
    public Integer expToLevelUp(final Integer cardLevel) {
        final Integer base = 100; // 等级为1时需要100经验值
        final double IncreaseRate = 1.05;
        final double result = Math.round(Math.pow(IncreaseRate, cardLevel - 1) * base); // 小数采用四舍五入法
        System.out.println(result);
        return (int) result;
    }

    @Override
    public JSONObject ListPage(ListRequest listRequest) {
        return OwnCardDao.ListPage(listRequest.getPageToken(), listRequest.getPageSize());
    }

    // 用户拥有的某张卡牌升级
    // @Override
    // public OwnCard cardLevelUp(Integer userId, Integer cardId) {
    // return OwnCardDao.cardLevelUp(userId, cardId);
    // }

    // 用户再一次拥有已经拥有的卡牌
    // @Override
    // public OwnCard ownAnotherCard(Integer userId, Integer cardId) {
    // return OwnCardDao.ownAnotherCard(userId, cardId);
    // }
}
