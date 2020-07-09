package com.example.accessingdatamysql.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "Mail", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "mailId")
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mailId;

    private String mailName;

    @Transient
    private MailDetails mailDetails;

    public Mail(){}

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
