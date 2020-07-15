package com.example.accessingdatamysql.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Admin", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "adminId")
public class Admin {
    // 管理员Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer adminId;
    // 管理员用户名
    @Column(unique = true)
    private String adminName;
    // 管理员密码
    private String password;
    // 管理员等级（editor跟admin，editor拥有更低的管理权限）
    private Integer role;

    public Admin() {
    };

    public Admin(String adminName, String password, Integer role) {
        this.adminName = adminName;
        this.password = password;
        this.role = role;
    }

    public void setAdmin(String adminName, String password, Integer role) {
        this.adminName = adminName;
        this.password = password;
        this.role = role;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
