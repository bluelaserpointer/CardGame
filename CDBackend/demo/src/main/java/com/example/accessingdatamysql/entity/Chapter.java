package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@IdClass(ChapterEntityId.class)
public class Chapter {
    @Id
    @Column(name = "chapter_id")
    private Integer chapterId;

    @Id
    @Column(name = "phase_no")
    private Integer phaseNo;

    private Integer phaseType;

    public Integer getPhaseType() {
        return phaseType;
    }

    public void setPhaseType(Integer phaseType) {
        this.phaseType = phaseType;
    }


    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getPhaseNo() {
        return phaseNo;
    }

    public void setPhaseNo(Integer phaseNo) {
        this.phaseNo = phaseNo;
    }
}
