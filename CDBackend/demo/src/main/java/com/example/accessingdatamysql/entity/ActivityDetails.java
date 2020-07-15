package com.example.accessingdatamysql.entity;

import java.sql.Timestamp;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ActivityDetails")
public class ActivityDetails {
    // 活动Id
    private Integer activityId;
    // 活动图片（可能没有）
    private String activityImg;
    // 活动描述
    private String activityDescription;
    // 活动开始的时间
    private Timestamp start;

    public ActivityDetails(Integer activityId, String activityImg, String activityDescription, Timestamp start) {
        this.activityId = activityId;
        this.activityImg = activityImg;
        this.activityDescription = activityDescription;
        this.start = start;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    @Id
    private ObjectId id;

    public String getActivityImg() {
        return this.activityImg;
    }

    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
    }

    public String getActivityDescription() {
        return this.activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public Timestamp getStart() {
        return this.start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

}
