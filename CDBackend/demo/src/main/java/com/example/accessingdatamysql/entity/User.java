package com.example.accessingdatamysql.entity;

// import java.util.Map;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
@org.hibernate.annotations.Proxy(lazy = false)
public class User {
    // 用户Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    // 用户名
    @Column(unique = true)
    private String userName;
    // 用户邮箱地址
    private String email;
    // 用户密码
    private String password;
    // 用户手机号码
    private String phoneNumber;
    // 用户信用（可删）
    private Integer credits;
    // 用户权限
    private Boolean access;
    // 用户等级
    private Integer level;
    // 用户目前经验值（每升一级清零【理论上来说】）
    private Integer curExpPoint;
    // 体力值
    private Integer stamina;
    // 氪金金币
    private Integer money;
    // 学生绩点（普通货币）
    private Double grade;
    // 英语知识点
    private Integer engKnowledge;
    // 数学知识点
    private Integer mathKnowledge;
    // 中文知识点
    private Integer chiKnowledge;
    // Identity 要写成 "ROLE_ADMIN","ROLE_USER"
    public static final String ROLE_ADMIN = "ROLE_ADMIN", ROLE_USER = "ROLE_USER";
    private String identity;

    public User() {
        this.credits = 0;
        this.access = true;
        this.level = 1;
    };

    public User(String userName, String email, String password, String phoneNumber, String identity) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.credits = 0;
        this.access = true;
        this.level = 1;
        this.curExpPoint = 0; // 初始时应该等级为1且经验值为0
        // TODO 这里需要确定一下每个等级的stamina是多少来确定初始化时的stamina值
        this.stamina = 100;
        this.money = 0;
        this.grade = 0.0;
        this.engKnowledge = 0;
        this.mathKnowledge = 0;
        this.chiKnowledge = 0;
        this.identity = identity;
    }

    public void updateUser(String userName, String email, String password, String phoneNumber, Integer credits,
            Boolean access, Integer level, Integer curExpPoint, Integer stamina, Integer money, Double grade,
            Integer engKnowledge, Integer mathKnowledge, Integer chiKnowledge, String identity) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.credits = credits;
        this.access = access;
        this.level = level;
        this.curExpPoint = curExpPoint;
        this.stamina = stamina;
        this.money = money;
        this.grade = grade;
        this.engKnowledge = engKnowledge;
        this.mathKnowledge = mathKnowledge;
        this.chiKnowledge = chiKnowledge;
        this.identity = identity;
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

    public Integer getCurExpPoint() {
        return curExpPoint;
    }

    public void setCurExpPoint(Integer curExpPoint) {
        this.curExpPoint = curExpPoint;
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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

}
