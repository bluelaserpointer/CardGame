package com.example.accessingdatamysql.dao;

import java.util.List;

import com.example.accessingdatamysql.Classes.PaginationDao;
import com.example.accessingdatamysql.entity.*;

public interface OwnCardDao extends PaginationDao {
        // 用ownCardId找某一个用户拥有卡牌的关系
        OwnCard getOneOwnCard(Integer OwncardId);

        // 增加一个用户拥有某张卡牌的关系
        OwnCard addNewOwnCard(Integer userId, Integer cardId);

        // 更新一个用户拥有某张卡牌的所有信息
        OwnCard updateOwnCard(OwnCard updateOwnCard);

        OwnCard redistributeUpgrades(OwnCard updateOwnCard);

        // 用户拥有的某张卡牌升级
        OwnCard cardLevelUp(Integer userId, Integer cardId);

        // 用户再一次拥有已经拥有的卡牌
        OwnCard ownAnotherCard(OwnCard ownCard);

        OwnCard findOwnCardByUserIdEqualsAndCardIdEquals(Integer userId, Integer cardId);

        // 获取所有的用户拥有卡牌记录
        List<OwnCard> getAllOwnCards();

        // 获取指定用户的所有拥有卡牌记录
        List<OwnCard> getAllOwnCardsByUserId(Integer userId);

        // 用ownCardIds来删除拥有卡牌关系
        String deleteOwnCards(List<Integer> OwncardIds);

        // 删除所有拥有卡牌关系
        String deleteAll();

        // 删除单个拥有卡牌关系
        List<OwnCard> deleteOwnCard(Integer userId, Integer cardId);

        Integer calculateLeftPoints(OwnCard ownCard);

}
