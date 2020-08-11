package com.example.accessingdatamysql.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CrashReportsDetail")
public class CrashReportsDetail {
    private Integer reportId;
    private String stackTrace;
    private String deviceInfo;

    public CrashReportsDetail() {}

    public CrashReportsDetail(Integer reportId, String stackTrace, String deviceInfo) {
        this.reportId = reportId;
        this.stackTrace = stackTrace;
        this.deviceInfo = deviceInfo;
    }

    public Integer getReportId() {
        return reportId;
    }
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
    public String getStackTrace() {
        return stackTrace;
    }
    public void setDeviceInfo(String deviceInfo) { this.deviceInfo = deviceInfo; }
    public String getDeviceInfo() { return deviceInfo; }
}
