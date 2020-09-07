package com.example.accessingdatamysql.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MailDetails")
public class MailDetails {
    // 邮件Id
    private Integer mailId;
    // 邮件图片
    private String mailImg;
    // 邮件描述
    private String mailDescription;

    @Id
    private ObjectId id;

    public MailDetails() {
    }

    public MailDetails(Integer mailId, String mailImg, String mailDescription) {
        this.mailId = mailId;
        this.mailImg = mailImg;
        this.mailDescription = mailDescription;
    }

    public Integer getMailId() {
        return mailId;
    }

    public void setMailId(Integer mailId) {
        this.mailId = mailId;
    }

    public String getMailImg() {
        return this.mailImg;
    }

    public void setMailImg(String mailImg) {
        this.mailImg = mailImg;
    }

    public String getMailDescription() {
        return this.mailDescription;
    }

    public void setMailDescription(String mailDescription) {
        this.mailDescription = mailDescription;
    }

}
