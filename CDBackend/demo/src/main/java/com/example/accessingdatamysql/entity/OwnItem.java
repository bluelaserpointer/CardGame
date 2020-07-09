
package com.example.accessingdatamysql.entity;

import javax.persistence.Entity;

import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "OwnItem", schema = "itemgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "ownItemId")

public class OwnItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ownItemId;

    private Integer userId;

    private Integer itemId;

    private Integer ItemCount;

    private Timestamp accquireDate;

    public OwnItem() {
    };

    public OwnItem(Integer userId, Integer itemId, Integer ItemCount, Timestamp accquireDate) {
        this.userId = userId;
        this.itemId = itemId;
        this.ItemCount = ItemCount;
        this.accquireDate = accquireDate;
    }

    public void setOwnItem(Integer userId, Integer itemId, Integer ItemCount, Timestamp accquireDate) {
        this.userId = userId;
        this.itemId = itemId;
        this.ItemCount = ItemCount;
        this.accquireDate = accquireDate;
    }

    public Integer getOwnItemId() {
        return ownItemId;
    }

    public void setOwnItemId(Integer ownItemId) {
        this.ownItemId = ownItemId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;

    }

    public Integer getItemCount() {
        return ItemCount;
    }

    public void setItemCount(Integer ItemCount) {
        this.ItemCount = ItemCount;

    }

    public Timestamp getAccquireDate() {
        return accquireDate;
    }

    public void setAccquireDate(Timestamp accquireDate) {
        this.accquireDate = accquireDate;
    }

}