package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "online_count_record", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "recordTime")
@org.hibernate.annotations.Proxy(lazy = false)
public class OnlineCountRecord {

    @Id
    private Timestamp recordTime;

    private Integer onlineCount;
    private Integer inPlayCount;

    public OnlineCountRecord() {
    };

    public OnlineCountRecord(Integer onlineCount, Integer inPlayCount) {
        this.recordTime = new Timestamp(System.currentTimeMillis());
        this.onlineCount = onlineCount;
        this.inPlayCount = inPlayCount;
    }

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public Integer getInPlayCount() {
        return inPlayCount;
    }

    public void setInPlayCount(Integer inPlayCount) {
        this.inPlayCount = inPlayCount;
    }

}
