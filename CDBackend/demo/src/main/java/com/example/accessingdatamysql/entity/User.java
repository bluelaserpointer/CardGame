package com.example.accessingdatamysql.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "User", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(unique = true)
    private String userName;

    private String email;

    private String password;

    private String phoneNumber;

    private Integer credits;

    private Boolean access;

    private Integer level;

    public User() {
        this.credits = 0;
        this.access = true;
        this.level = 1;
    };

    public User(String userName, String email, String password, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.credits = 0;
        this.access = true;
        this.level = 1;

    }

    public void updateUser(String userName, String email, String password, String phoneNumber, Integer credits,
            Boolean access, Integer level) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.credits = credits;
        this.access = access;
        this.level = level;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getAccess() {
        return this.access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    // public MailBox getMailBox() {
    // return mailBox;
    // }

    // public void setMailBox(MailBox mailBox) {
    // this.mailBox = mailBox;
    // }

    // public FriendList getFriendList() {
    // return friendList;
    // }

    // public void setFriendList(FriendList friendList) {
    // this.friendList = friendList;
    // }

}