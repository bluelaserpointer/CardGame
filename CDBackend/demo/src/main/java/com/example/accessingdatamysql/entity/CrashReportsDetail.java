package com.example.accessingdatamysql.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CrashReportsDetail")
public class CrashReportsDetail {
    private Integer reportId;
    private String content;

    public CrashReportsDetail(){}

    public CrashReportsDetail(Integer reportId, String content) {
        this.reportId = reportId;
        this.content = content;
    }

    public Integer getReportId() {
        return reportId;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }
}
