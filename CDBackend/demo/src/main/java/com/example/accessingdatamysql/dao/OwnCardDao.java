package com.example.accessingdatamysql.dao;

import java.sql.Timestamp;
// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface OwnCardDao {
        // 用ownCardId找某一个用户拥有卡牌的关系
        OwnCard getOneOwnCard(Integer OwncardId);

        // 增加一个用户拥有某张卡牌的关系
        String addNewOwnCard(Integer userId, Integer cardId);

        // 更新一个用户拥有某张卡牌的所有信息
        OwnCard updateOwnCard(Integer userId, Integer cardId, Integer cardLevel, Integer cardCurExp,
                        Integer cardLevelLimit, Integer repetitiveOwns, Timestamp accquireDate);

        // 用户拥有的某张卡牌升级
        OwnCard cardLevelUp(Integer userId, Integer cardId);

        // 用户重复拥有一个
        OwnCard ownAnotherCard(Integer userId, Integer cardId);

        List<OwnCard> getAllOwnCards();

        List<OwnCard> getAllOwnCardsByUserId(Integer userId);

        String deleteOwnCards(List<Integer> OwncardIds);

        String deleteAll();

        List<OwnCard> deleteOwnCard(Integer userId, Integer cardId);

}
