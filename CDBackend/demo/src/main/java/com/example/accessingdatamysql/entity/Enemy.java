package com.example.accessingdatamysql.entity;

import javax.persistence.Entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Enemy", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "enemyId")

public class Enemy {
    // 敌人Id（PK）
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer enemyId;
    // 敌人名称
    private String enemyName;
    // 敌人的hp属性
    private Integer healthPoint;
    // 敌人攻击属性
    private Integer attack;
    // 敌人防御属性
    private Integer defense;
    // 敌人攻击范围
    private Integer attackRange;
    // 敌人冷却时间（可能不需要）
    private Double cd;
    // 敌人速度属性
    private Integer speed;

    public Enemy() {
    };

    public Enemy(String enemyName, Integer healthPoint, Integer attack, Integer defense, Integer attackRange, Double cd,
            Integer speed) {
        this.enemyName = enemyName;
        this.healthPoint = healthPoint;
        this.attack = attack;
        this.defense = defense;
        this.attackRange = attackRange;
        this.cd = cd;
        this.speed = speed;
    }

    // When modifying enemy
    public void setEnemy(String enemyName, Integer healthPoint, Integer attack, Integer defense, Integer attackRange,
            Double cd, Integer speed) {
        this.enemyName = enemyName;
        this.healthPoint = healthPoint;
        this.attack = attack;
        this.defense = defense;
        this.attackRange = attackRange;
        this.cd = cd;
        this.speed = speed;
    }

    public Integer getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(Integer enemyId) {
        this.enemyId = enemyId;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(Integer healthPoint) {
        this.healthPoint = healthPoint;

    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String EnemyName) {
        this.enemyName = EnemyName;
    }

    public Integer getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(Integer attackRange) {
        this.attackRange = attackRange;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Double getCd() {
        return cd;
    }

    public void setCd(Double cd) {
        this.cd = cd;
    }

    @Transient
    private EnemyDetails enemyDetails;

    public EnemyDetails getEnemyDetails() {
        return enemyDetails;
    }

    public void setEnemyDetails(EnemyDetails enemyDetails) {
        this.enemyDetails = enemyDetails;
    }
}
