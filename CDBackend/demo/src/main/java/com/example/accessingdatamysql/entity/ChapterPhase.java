package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "chapter_phase", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@IdClass(ChapterPhaseEntityId.class)
public class ChapterPhase {
    @Id
    @Column(name = "chapter_id")
    private Integer chapterId;

    @Id
    @Column(name = "phase_id")
    private Integer phaseId;

    @Column
    @ElementCollection(targetClass = Integer.class)
    private Map<Integer, Integer> awardItems;

    @Column
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> awardCards;

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

    public List<Integer> getAwardCards() {
        return awardCards;
    }

    public void setAwardCards(List<Integer> awardCards) {
        this.awardCards = awardCards;
    }

    public Map<Integer, Integer> getAwardItems() {
        return awardItems;
    }

    public void setAwardItems(Map<Integer, Integer> awardItems) {
        this.awardItems = awardItems;
    }

}
