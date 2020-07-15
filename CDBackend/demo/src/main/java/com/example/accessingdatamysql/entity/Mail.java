package com.example.accessingdatamysql.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "Mail", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "mailId")
public class Mail {
    // 邮件Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mailId;
    // 邮件标题
    private String mailName;
    // 邮件详情（包括描述，图片等）
    @Transient
    private MailDetails mailDetails;

    public Mail() {
    }

    public Mail(String mailName) {
        this.mailName = mailName;
    }

    public Integer getMailId() {
        return mailId;
    }

    public void setMailId(Integer mailId) {
        this.mailId = mailId;
    }

    public String getMailName() {
        return this.mailName;
    }

    public void setMailName(String mailName) {
        this.mailName = mailName;
    }

    public MailDetails getMailDetails() {
        return this.mailDetails;
    }

    public void setMailDetails(MailDetails mailDetails) {
        this.mailDetails = mailDetails;
    }

}
