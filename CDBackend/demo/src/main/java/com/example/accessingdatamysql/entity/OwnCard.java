package com.example.accessingdatamysql.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "OwnCard", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "ownCardId")
public class OwnCard {
    // 拥有卡牌这一关系的Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ownCardId;
    // 拥有卡片的用户的Id
    private Integer userId;
    // 用户所拥有的卡片的Id
    private Integer cardId;
    // 用户拥有的某张卡片的等级
    private Integer cardLevel;
    // 用户拥有的某张卡牌的目前的经验值（升级清零）
    private Integer cardCurExp;
    // 用户目前拥有的这张卡牌的等级上限
    private Integer cardLevelLimit;
    // 用户目前重复拥有这张卡拍的数目
    private Integer repetitiveOwns;
    // 用户首次获取该卡牌的日期+时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp accquireDate;

    public OwnCard() {
    };

    public OwnCard(Integer userId, Integer cardId, Timestamp accquireDate) {
        this.userId = userId;
        this.cardId = cardId;
        this.accquireDate = accquireDate;
        this.cardLevel = 1; // 首次获得卡牌等级为1
        this.cardCurExp = 0; // 首次获得经验值为0
        // 等级上限的确切机制需要进行讨论，讨论后需要在这里添加初始化时卡牌的等级上限
        this.repetitiveOwns = 1; // 首次获得拥有一张
    }

    // 作为一个整体修改用户拥有卡牌的数据（可能有一部分数据可以省略，因为基本上不会被修改）
    public void setOwnCard(Integer userId, Integer cardId, Integer cardLevel, Integer cardCurExp,
            Integer cardLevelLimit, Integer repetitiveOwns, Timestamp accquireDate) {
        this.userId = userId;
        this.cardId = cardId;
        this.cardLevel = cardLevel;
        this.cardCurExp = cardCurExp;
        this.cardLevelLimit = cardLevelLimit;
        this.repetitiveOwns = repetitiveOwns;
        this.accquireDate = accquireDate;
    }

    public Integer getOwnCardId() {
        return ownCardId;
    }

    public void setOwnCardId(Integer ownCardId) {
        this.ownCardId = ownCardId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(Integer cardLevel) {
        this.cardLevel = cardLevel;
    }

    public Integer getCardCurExp() {
        return cardCurExp;
    }

    public void setCardCurExp(Integer cardCurExp) {
        this.cardCurExp = cardCurExp;
    }

    public Integer getCardLevelLimit() {
        return cardLevelLimit;
    }

    public void setCardLevelLimit(Integer cardLevelLimit) {
        this.cardLevelLimit = cardLevelLimit;
    }

    public Integer getRepetitiveOwns() {
        return repetitiveOwns;
    }

    public void setRepetitiveOwns(Integer repetitiveOwns) {
        this.repetitiveOwns = repetitiveOwns;
    }

    public Timestamp getAccquireDate() {
        return accquireDate;
    }

    public void setAccquireDate(Timestamp accquireDate) {
        this.accquireDate = accquireDate;
    }

}
