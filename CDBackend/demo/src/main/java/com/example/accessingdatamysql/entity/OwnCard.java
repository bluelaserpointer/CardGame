package com.example.accessingdatamysql.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "own_card", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "ownCardId")
public class OwnCard {
    // 拥有卡牌这一关系的Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    // 强化点数
    private Integer enhancePoint;
    // 剩余强化点数
    private Integer leftPoints;
    // 强化hp次数
    private Integer enhanceHP;
    // 强化攻击次数
    private Integer enhanceAttack;
    // 强化防御次数
    private Integer enhanceDefense;
    // 强化攻击范围次数
    private Integer enhanceAttackRange;
    // 强化冷却时间
    private Integer enhanceCD;
    // 强化速度次数
    private Integer enhanceSpeed;

    public OwnCard() {
    };

    public OwnCard(Integer userId, Integer cardId, Timestamp accquireDate) {
        this.userId = userId;
        this.cardId = cardId;
        this.accquireDate = accquireDate;
        this.cardLevel = 1; // 首次获得卡牌等级为1
        this.cardCurExp = 0; // 首次获得经验值为0
        this.cardLevelLimit = 50; // 一开始是50级，然后每提升卡牌张数+1
        this.repetitiveOwns = 1; // 首次获得拥有一张
        this.enhancePoint = 0;
        this.leftPoints = 0;
        this.enhanceHP = 0;
        this.enhanceAttack = 0;
        this.enhanceDefense = 0;
        this.enhanceAttackRange = 0;
        this.enhanceCD = 0;
        this.enhanceSpeed = 0;
    }

    // 作为一个整体修改用户拥有卡牌的数据（可能有一部分数据可以省略，因为基本上不会被修改）
    public void setOwnCard(Integer userId, Integer cardId, Integer cardLevel, Integer cardCurExp,
            Integer cardLevelLimit, Integer repetitiveOwns, Timestamp accquireDate, Integer enhancePoint,
            Integer leftPoints, Integer enhanceHP, Integer enhanceAttack, Integer enhanceDefense,
            Integer enhanceAttackRange, Integer enhanceCD, Integer enhanceSpeed) {
        this.userId = userId;
        this.cardId = cardId;
        this.cardLevel = cardLevel;
        this.cardCurExp = cardCurExp;
        this.cardLevelLimit = cardLevelLimit;
        this.repetitiveOwns = repetitiveOwns;
        this.accquireDate = accquireDate;
        this.enhancePoint = enhancePoint;
        this.leftPoints = leftPoints;
        this.enhanceHP = enhanceHP;
        this.enhanceAttack = enhanceAttack;
        this.enhanceDefense = enhanceDefense;
        this.enhanceAttackRange = enhanceAttackRange;
        this.enhanceCD = enhanceCD;
        this.enhanceSpeed = enhanceSpeed;
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

    public Integer getEnhancePoint() {
        return enhancePoint;
    }

    public void setEnhancePoint(Integer enhancePoint) {
        this.enhancePoint = enhancePoint;
    }

    public Integer getLeftPoints() {
        return leftPoints;
    }

    public void setLeftPoints(Integer leftPoints) {
        this.leftPoints = leftPoints;
    }

    public Integer getEnhanceHP() {
        return enhanceHP;
    }

    public void setEnhanceHP(Integer enhanceHP) {
        this.enhanceHP = enhanceHP;
    }

    public Integer getEnhanceAttack() {
        return enhanceAttack;
    }

    public void setEnhanceAttack(Integer enhanceAttack) {
        this.enhanceAttack = enhanceAttack;
    }

    public Integer getEnhanceDefense() {
        return enhanceDefense;
    }

    public void setEnhanceDefense(Integer enhanceDefense) {
        this.enhanceDefense = enhanceDefense;
    }

    public Integer getEnhanceAttackRange() {
        return enhanceAttackRange;
    }

    public void setEnhanceAttackRange(Integer enhanceAttackRange) {
        this.enhanceAttackRange = enhanceAttackRange;
    }

    public Integer getEnhanceCD() {
        return enhanceCD;
    }

    public void setEnhanceCD(Integer enhanceCD) {
        this.enhanceCD = enhanceCD;
    }

    public Integer getEnhanceSpeed() {
        return enhanceSpeed;
    }

    public void setEnhanceSpeed(Integer enhanceSpeed) {
        this.enhanceSpeed = enhanceSpeed;
    }

}
