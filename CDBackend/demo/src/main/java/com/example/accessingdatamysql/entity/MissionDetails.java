package com.example.accessingdatamysql.entity;

// import java.sql.Timestamp;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MissionDetails")
public class MissionDetails {
    private Integer MissionId;

    private String MissionDescription;

    public MissionDetails(Integer MissionId, String MissionDescription) {
        this.MissionId = MissionId;
        this.MissionDescription = MissionDescription;
    }

    public Integer getMissionId() {
        return MissionId;
    }

    public void setMissionId(Integer MissionId) {
        this.MissionId = MissionId;
    }

    @Id
    private ObjectId id;

    public String getMissionDescription() {
        return this.MissionDescription;
    }

    public void setMissionDescription(String MissionDescription) {
        this.MissionDescription = MissionDescription;
    }

}
