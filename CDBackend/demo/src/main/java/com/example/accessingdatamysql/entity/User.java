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
    // 体力值
    private Integer stamina;
    // 氪金金币
    private Integer money;
    // 学生绩点
    private Double grade;

    private Integer engKnowledge;

    private Integer mathKnowledge;

    private Integer chiKnowledge;

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
        this.money = 0;
        this.engKnowledge = 0;
        this.mathKnowledge = 0;
        this.chiKnowledge = 0;
    }

    public void updateUser(String userName, String email, String password, String phoneNumber, Integer credits,
            Boolean access, Integer level, Integer stamina, Integer money, Double grade, Integer engKnowledge, Integer mathKnowledge,
            Integer chiKnowledge) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.credits = credits;
        this.access = access;
        this.level = level;
        this.stamina = stamina;
        this.money = money;
        this.grade = grade;
        this.engKnowledge = engKnowledge;
        this.mathKnowledge = mathKnowledge;
        this.chiKnowledge = chiKnowledge;
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

    public Integer getStamina() {
        return stamina;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getEngKnowledge() {
        return engKnowledge;
    }

    public void setEngKnowledge(Integer engKnowledge) {
        this.engKnowledge = engKnowledge;
    }

    public Integer getMathKnowledge() {
        return mathKnowledge;
    }

    public void setMathKnowledge(Integer mathKnowledge) {
        this.mathKnowledge = mathKnowledge;
    }

    public Integer getChiKnowledge() {
        return chiKnowledge;
    }

    public void setChiKnowledge(Integer chiKnowledge) {
        this.chiKnowledge = chiKnowledge;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
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
