package com.example.accessingdatamysql.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Item", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "itemId")
public class Item {
    // 道具Id（PK）
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer itemId;
    // 道具名称
    @Column(unique = true)
    private String itemName;
    // 道具价格
    private Integer price;

    public Item() {
    };

    public Item(String itemName, Integer price) {
        this.itemName = itemName;
        this.price = price;
    }

    public void setItem(String itemName, Integer price) {
        this.itemName = itemName;
        this.price = price;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Transient
    private ItemDetails ItemDetails;

    public ItemDetails getItemDetails() {
        return ItemDetails;
    }

    public void setItemDetails(ItemDetails ItemDetails) {
        this.ItemDetails = ItemDetails;
    }
}