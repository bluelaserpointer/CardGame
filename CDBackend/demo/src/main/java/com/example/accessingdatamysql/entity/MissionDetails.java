package com.example.accessingdatamysql.entity;

// import java.sql.Timestamp;
// import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MissionDetails")
public class MissionDetails {
    // 任务Id
    private Integer missionId;
    // 任务描述
    private String missionDescription;

    public MissionDetails() {
    }

    public MissionDetails(Integer missionId, String missionDescription) {
        this.missionId = missionId;
        this.missionDescription = missionDescription;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    @Id
    private ObjectId id;

    public String getMissionDescription() {
        return this.missionDescription;
    }

    public void setMissionDescription(String missionDescription) {
        this.missionDescription = missionDescription;
    }

}
