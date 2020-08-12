package com.example.accessingdatamysql.entity;

import javax.persistence.Entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "pve_record", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pveRecordId")

public class PveRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pveRecordId;
    private Integer userId;
    private Integer chapterId;
    private Integer phaseId;
    private Integer result;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp recordTime;

    @Column
    @ElementCollection(targetClass = Integer.class)
    private Map<Integer, Integer> posRecord;

    public PveRecord() {
    }

    public PveRecord(Integer userId, Integer chapterId, Integer phaseId, Integer result) {
        this.userId = userId;
        this.chapterId = chapterId;
        this.phaseId = phaseId;
        this.result = result;
    }

    public void setPveRecord(Integer userId, Integer chapterId, Integer phaseId, Integer result, Timestamp recordTime) {
        this.userId = userId;
        this.chapterId = chapterId;
        this.phaseId = phaseId;
        this.result = result;
        this.recordTime = recordTime;
    }

    public Integer getPveRecordId() {
        return pveRecordId;
    }

    public void setPveRecordId(Integer pveRecordId) {
        this.pveRecordId = pveRecordId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Integer phaseId) {
        this.phaseId = phaseId;
    }

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Map<Integer, Integer> getPosRecord() {
        return posRecord;
    }

    public void setPosRecord(Map<Integer, Integer> posRecord) {
        this.posRecord = posRecord;
    }
}
