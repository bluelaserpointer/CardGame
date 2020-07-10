package com.example.accessingdatamysql.entity;

import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "MissionList", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "missionListId")

public class MissionList {
    @Id
    @Column(name = "missionListId", nullable = false)
    private Integer missionListId;

    @ManyToMany
    @JoinColumn(name = "missions", unique = false)
    private List<Mission> missions;

    // MissionListid应该要等于userId（一对一关系，（不用自动生成的方法了））
    public MissionList(Integer MissionListId) {
        this.missionListId = MissionListId;
    }

    public Integer getMissionListId() {
        return missionListId;

    }

    // 应该不会更改MissionListid
    public void setMissionListId(Integer MissionListId) {
        this.missionListId = MissionListId;
    }

    public List<Mission> getMissions() {
        return this.missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

}