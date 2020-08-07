package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.CrashReports;

import java.util.List;

public interface CrashReportService {
    CrashReports getOne(Integer reportId);

    void addNew(String reportContent);

    List<CrashReports> getCrashReportsWithinHalfYear();

    List<CrashReports> getCrashReportsWithinOneDay();

}
