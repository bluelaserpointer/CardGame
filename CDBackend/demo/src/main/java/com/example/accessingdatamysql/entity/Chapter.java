package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "chapter", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
public class Chapter {
    @Id
    @Column(name = "chapter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chapterId;

    @Column
    @ElementCollection(targetClass = Integer.class)
    private Map<Integer, Integer> awardItems;

    @Column
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> awardCards;

    public Chapter() {
    }

    private Integer phaseNo;

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Map<Integer, Integer> getAwardItems() {
        return awardItems;
    }

    public void setAwardItems(Map<Integer, Integer> awardItems) {
        this.awardItems = awardItems;
    }

    public List<Integer> getAwardCards() {
        return awardCards;
    }

    public void setAwardCards(List<Integer> awardCards) {
        this.awardCards = awardCards;
    }

    public Integer getPhaseNo() {
        return phaseNo;
    }

    public void setPhaseNo(Integer phaseNo) {
        this.phaseNo = phaseNo;
    }

    public Integer getPhaseType() {
        return phaseType;
    }

    public void setPhaseType(Integer phaseType) {
        this.phaseType = phaseType;
    }

    private Integer phaseType;

}
