package com.example.accessingdatamysql.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "OwnCard", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "ownCardId")
public class OwnCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ownCardId;
    private Integer userId;

    private Integer cardId;

    private Timestamp accquireDate;

    public OwnCard() {
    };

    public OwnCard(Integer userId, Integer cardId, Timestamp accquireDate) {
        this.userId = userId;
        this.cardId = cardId;
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

    public Timestamp getAccquireDate() {
        return accquireDate;
    }

    public void setAccquireDate(Timestamp accquireDate) {
        this.accquireDate = accquireDate;
    }

}