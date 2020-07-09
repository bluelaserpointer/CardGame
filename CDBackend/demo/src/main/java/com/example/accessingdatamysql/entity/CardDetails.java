package com.example.accessingdatamysql.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CardDetails")
public class CardDetails {
    private Integer cardId;

    private String cardImg;

    private String cardDescription;

    private String shortDescription;

    public CardDetails(Integer cardId, String cardImg, String shortDescription, String cardDescription) {
        this.cardId = cardId;
        this.cardImg = cardImg;
        this.shortDescription = shortDescription;
        this.cardDescription = cardDescription;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    @Id
    private ObjectId id;

    public String getCardImg() {
        return this.cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public String getCardDescription() {
        return this.cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

}