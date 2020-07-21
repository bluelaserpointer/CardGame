
package com.example.accessingdatamysql.entity;

import javax.persistence.Entity;

import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "OwnItem", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "ownItemId")

public class OwnItem {
    // 拥有道具关系Id（PK）
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ownItemId;
    // 拥有道具的用户的userId
    private Integer userId;
    // 用户拥有的道具的itemId
    private Integer itemId;
    // 用户拥有道具的数量
    private Integer ItemCount;
    // 用户（首次）拥有道具的时间（也可以修改成最新一次重复拥有的时间）
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
