package com.example.accessingdatamysql.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ActivityDetails")
public class ActivityDetails {
    // 活动Id
    private Integer activityId;
    // 活动图片（可能没有）
    private String activityImg;
    // 活动描述
    private String activityDescription;

    public ActivityDetails() {
    }

    public ActivityDetails(Integer activityId, String activityImg, String activityDescription) {
        this.activityId = activityId;
        this.activityImg = activityImg;
        this.activityDescription = activityDescription;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

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

}
