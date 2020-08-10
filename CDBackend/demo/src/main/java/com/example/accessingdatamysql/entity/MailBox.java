package com.example.accessingdatamysql.entity;

import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "MailBox", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "mailBoxId")

public class MailBox {
    // 邮箱Id（应该保持跟用户的userId一样）
    @Id
    @Column(name = "mailBoxId", nullable = false)
    private Integer mailBoxId;
    // 邮箱内村的邮箱
    @Column
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> mailIds;

    public MailBox() {

    }

    // mailBoxid应该要等于userId（一对一关系，（不用自动生成的方法了））
    public MailBox(Integer mailBoxId) {
        this.mailBoxId = mailBoxId;
    }

    public Integer getMailBoxId() {
        return mailBoxId;

    }

    // 应该不会更改mailboxid
    public void setMailBoxId(Integer mailBoxId) {
        this.mailBoxId = mailBoxId;
    }

    public List<Integer> getMailIds() {
        return this.mailIds;
    }

    public void setMails(List<Integer> mailIds) {
        this.mailIds = mailIds;
    }

}