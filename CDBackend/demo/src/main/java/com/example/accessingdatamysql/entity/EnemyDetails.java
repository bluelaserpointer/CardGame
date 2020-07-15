package com.example.accessingdatamysql.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "EnemyDetails")
public class EnemyDetails {
    private Integer enemyId;

    private String enemyImg;

    private String enemyDescription;

    private String shortDescription;

    public EnemyDetails(Integer enemyId, String enemyImg, String shortDescription, String enemyDescription) {
        this.enemyId = enemyId;
        this.enemyImg = enemyImg;
        this.shortDescription = shortDescription;
        this.enemyDescription = enemyDescription;
    }

    public Integer getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(Integer enemyId) {
        this.enemyId = enemyId;
    }

    @Id
    private ObjectId id;

    public String getEnemyImg() {
        return this.enemyImg;
    }

    public void setEnemyImg(String enemyImg) {
        this.enemyImg = enemyImg;
    }

    public String getEnemyDescription() {
        return this.enemyDescription;
    }

    public void setEnemyDescription(String enemyDescription) {
        this.enemyDescription = enemyDescription;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

}