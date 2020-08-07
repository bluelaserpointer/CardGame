package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "CrashReports", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "reportId")
public class CrashReports {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reportId;

    private Timestamp recordTime;

    @Transient
    private CrashReportsDetail detail;

    private Boolean checked;

    public Integer getReportId() {
        return reportId;
    }
    public void setDetail(CrashReportsDetail detail) {
        this.detail = detail;
    }
    public CrashReportsDetail getDetail() {
        return detail;
    }
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
    public Boolean getChecked() {
        return checked;
    }
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }
    public Timestamp getRecordTime() {
        return recordTime;
    }
    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }
}
