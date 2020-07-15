package com.example.accessingdatamysql.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ItemDetails")
public class ItemDetails {
    // 道具Id
    private Integer itemId;
    // 道具图片
    private String itemImg;
    // 道具描述
    private String itemDescription;

    public ItemDetails(Integer itemId, String itemImg, String itemDescription) {
        this.itemId = itemId;
        this.itemImg = itemImg;
        this.itemDescription = itemDescription;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Id
    private ObjectId id;

    public String getItemImg() {
        return this.itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

}