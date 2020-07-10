package com.example.accessingdatamysql.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Mission", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "missionId")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer missionId;

    private String MissionName;

    private String type;

    @Column
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> awardItems;

    @Transient
    private MissionDetails MissionDetails;

    public Mission() {
    }

    public Mission(String type, String MissionName) {
        this.type = type;
        this.MissionName = MissionName;
    }

    public void setMission(String type, String MissionName) {
        this.type = type;
        this.MissionName = MissionName;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer MissionId) {
        this.missionId = MissionId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getMissionName() {
        return this.MissionName;
    }

    public void setMissionName(String MissionName) {
        this.MissionName = MissionName;
    }

    public MissionDetails getMissionDetails() {
        return this.MissionDetails;
    }

    public void setMissionDetails(MissionDetails MissionDetails) {
        this.MissionDetails = MissionDetails;
    }

    public List<Integer> getAwardItems() {
        return this.awardItems;
    }

    public void setAwardItems(List<Integer> awardItems) {
        this.awardItems = awardItems;
    }

}
