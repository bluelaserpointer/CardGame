package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "UserLoginRecord", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userLoginRecordId")
@org.hibernate.annotations.Proxy(lazy = false)
public class UserLoginRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userLoginRecordId;

    private Integer userId;

    private Timestamp loginTime;
    private Timestamp logoutTime;

    // 正常登入登出，系统IDLE踢出
    private Integer type;

    public UserLoginRecord() {
    };

    public UserLoginRecord(Integer userId) {
        this.loginTime = new Timestamp(System.currentTimeMillis());
        this.userId = userId;
    }

    public void setUserLoginRecord(Integer type)
    {
        this.logoutTime = new Timestamp(System.currentTimeMillis());
        this.type = type;
    }

    public Integer getUserLoginRecordId() {
        return userLoginRecordId;
    }

    public void setUserLoginRecordId(Integer userLoginRecordId) {
        this.userLoginRecordId = userLoginRecordId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
