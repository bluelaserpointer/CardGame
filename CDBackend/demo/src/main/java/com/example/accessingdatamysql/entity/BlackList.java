package com.example.accessingdatamysql.entity;

import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "black_list", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "blackListId")

public class BlackList {
    // 黑名单Id（应该保持跟相对应的用户的userId相同）
    @Id
    @Column(name = "black_list_id", nullable = false)
    private Integer blackListId;
    // 在此用户的黑名单内的其他用户的userId
    @Column
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> blockIds;

    public BlackList() {}
    // blackListId应该要等于userId（一对一关系，（不用自动生成的方法了））
    public BlackList(Integer userId) {
        this.blackListId = userId;
    }

    public Integer getBlackListId() {
        return blackListId;

    }

    // 应该不会更改blackListid
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
