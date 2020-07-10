package com.example.accessingdatamysql.entity;

import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "BlackList", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "blackListId")

public class BlackList {
    @Id
    @Column(name = "blackListId", nullable = false)
    private Integer blackListId;

    @Column
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> blockIds;

    // friendListId应该要等于userId（一对一关系，（不用自动生成的方法了））
    public BlackList(Integer userId) {
        this.blackListId = userId;
    }

    public Integer getBlackListId() {
        return blackListId;

    }

    // 应该不会更改friendListid
    public void setBlackListId(Integer BlackListId) {
        this.blackListId = BlackListId;
    }

    public List<Integer> getBlockIds() {
        return this.blockIds;
    }

    public void setBlockIds(List<Integer> blockIds) {
        this.blockIds = blockIds;
    }

}