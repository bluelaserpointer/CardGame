package com.example.accessingdatamysql.entity;

import javax.persistence.Entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Card", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cardId")

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cardId;

    private String cardName;

    private String rarity;

    private Integer healthPoint;

    private Integer attack;

    private Integer defense;

    private Integer attackRange;

    private Double cd;

    private Integer speed;

    public Card() {
    };

    public Card(String rarity, String cardName, Integer healthPoint, Integer attack, Integer defense,
            Integer attackRange, Double cd, Integer speed) {
        this.rarity = rarity;
        this.cardName = cardName;
        this.healthPoint = healthPoint;
        this.attack = attack;
        this.defense = defense;
        this.attackRange = attackRange;
        this.cd = cd;
        this.speed = speed;
    }

    public void setCard(String rarity, String cardName, Integer healthPoint, Integer attack, Integer defense,
            Integer attackRange, Double cd, Integer speed) {
        this.rarity = rarity;
        this.cardName = cardName;
        this.healthPoint = healthPoint;
        this.attack = attack;
        this.defense = defense;
        this.attackRange = attackRange;
        this.cd = cd;
        this.speed = speed;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer CardId) {
        this.cardId = CardId;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(Integer healthPoint) {
        this.healthPoint = healthPoint;

    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public Integer getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(Integer attackRange) {
        this.attackRange = attackRange;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Double getCd() {
        return cd;
    }

    public void setCd(Double cd) {
        this.cd = cd;
    }

    @Transient
    private CardDetails CardDetails;

    public CardDetails getCardDetails() {
        return CardDetails;
    }

    public void setCardDetails(CardDetails CardDetails) {
        this.CardDetails = CardDetails;
    }

    // @OneToMany(mappedBy = "Card")
    // private Set<Cart> cartRecords;

    // public Set<Cart> getCartRecords() {
    // return this.cartRecords;
    // }

    // public void setCartRecords(Set<Cart> cartRecords) {
    // this.cartRecords = cartRecords;
    // }

}