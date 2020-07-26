package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.OwnCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class OwnCardServiceImpl implements OwnCardService {
    @Autowired
    private OwnCardDao OwnCardDao;

    // 用ownCardId找某一个用户拥有卡牌的关系
    @Override
    public OwnCard getOneOwnCard(Integer OwnCardId) {
        return OwnCardDao.getOneOwnCard(OwnCardId);
    }

    // 增加一个用户拥有某张卡牌的关系
    @Override
    public OwnCard addNewOwnCard(Integer userId, Integer cardId) {
        if (OwnCardDao.findOwnCardByUserIdEqualsAndCardIdEquals(userId, cardId) != null) {
            return OwnCardDao.ownAnotherCard(OwnCardDao.findOwnCardByUserIdEqualsAndCardIdEquals(userId, cardId));
        }
        return OwnCardDao.addNewOwnCard(userId, cardId);
    }

    // 获取指定用户的所有拥有卡牌记录
    public List<OwnCard> getAllOwnCardsByUserId(Integer userId) {
        return OwnCardDao.getAllOwnCardsByUserId(userId);
    }

    // 获取所有的用户拥有卡牌记录
    public List<OwnCard> getAllOwnCards() {
        return OwnCardDao.getAllOwnCards();
    }

    // 用ownCardIds来删除拥有卡牌关系
    public String deleteOwnCards(List<Integer> OwnCardIds) {
        return OwnCardDao.deleteOwnCards(OwnCardIds);
    }

    // 删除所有拥有卡牌关系
    public String deleteAll() {
        return OwnCardDao.deleteAll();
    }

    // 删除单个拥有卡牌关系
    public List<OwnCard> deleteOwnCard(Integer userId, Integer cardId) {
        return OwnCardDao.deleteOwnCard(userId, cardId);
    }

    // 更新一个用户拥有某张卡牌的所有信息
    @Override
    public OwnCard updateOwnCard(OwnCard updateOwnCard) {
        return OwnCardDao.updateOwnCard(updateOwnCard);
    }

    // 用户拥有的某张卡牌升级
    @Override
    public OwnCard cardLevelUp(Integer userId, Integer cardId) {
        return OwnCardDao.cardLevelUp(userId, cardId);
    }

    // 用户再一次拥有已经拥有的卡牌
    // @Override
    // public OwnCard ownAnotherCard(Integer userId, Integer cardId) {
    // return OwnCardDao.ownAnotherCard(userId, cardId);
    // }
}
