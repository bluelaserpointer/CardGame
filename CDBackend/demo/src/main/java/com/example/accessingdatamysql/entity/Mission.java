package com.example.accessingdatamysql.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "mission", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "missionId")
public class Mission {
    // 任务Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer missionId;
    // 任务名称
    private String missionName;
    // 任务类型（日常，后面的需要讨论）
    private String type;
    // 完成任务奖励的道具
    @Column
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> awardItems;
    // 任务详情（描述）
    @Transient
    private MissionDetails missionDetails;

    public Mission() {
    }

    public Mission(String type, String missionName) {
        this.type = type;
        this.missionName = missionName;
    }

    public void setMission(String type, String missionName) {
        this.type = type;
        this.missionName = missionName;
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
        return this.missionName;
    }

    public void setMissionName(String MissionName) {
        this.missionName = MissionName;
    }

    public MissionDetails getMissionDetails() {
        return this.missionDetails;
    }

    public void setMissionDetails(MissionDetails missionDetails) {
        this.missionDetails = missionDetails;
    }

    public List<Integer> getAwardItems() {
        return this.awardItems;
    }

    public void setAwardItems(List<Integer> awardItems) {
        this.awardItems = awardItems;
    }

}
