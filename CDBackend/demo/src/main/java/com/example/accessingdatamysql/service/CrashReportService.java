package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.CrashReports;

public interface CrashReportService {
    CrashReports getOne(Integer reportId);

    void addNew(String reportContent);
}
