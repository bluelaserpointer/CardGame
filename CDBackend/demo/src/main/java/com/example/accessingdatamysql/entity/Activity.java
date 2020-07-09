package com.example.accessingdatamysql.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Activity", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "activityId")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer activityId;

    private String activityName;

    private String type;

    @Transient
    private ActivityDetails activityDetails;

    public Activity(){
    }

    public Activity(String type, String activityName) {
        this.type = type;
        this.activityName = activityName;
    }

    public void setActivity(String type, String activityName) {
        this.type = type;
        this.activityName = activityName;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public ActivityDetails getActivityDetails() {
        return this.activityDetails;
    }

    public void setActivityDetails(ActivityDetails activityDetails) {
        this.activityDetails = activityDetails;
    }

}
