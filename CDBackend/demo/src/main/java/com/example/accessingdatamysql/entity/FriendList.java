package com.example.accessingdatamysql.entity;

import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "FriendList", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "friendListId")

public class FriendList {
    @Id
    @Column(name = "friendListId", nullable = false)
    private Integer friendListId;

    @Column
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> friendIds;

    // friendListId应该要等于userId（一对一关系，（不用自动生成的方法了））
    public FriendList(Integer userId) {
        this.friendListId = userId;
    }

    public Integer getFriendListId() {
        return friendListId;

    }

    // 应该不会更改friendListid
    public void setFriendListId(Integer FriendListId) {
        this.friendListId = FriendListId;
    }

    public List<Integer> getFriendIds() {
        return this.friendIds;
    }

    public void setFriendIds(List<Integer> friendIds) {
        this.friendIds = friendIds;
    }

}