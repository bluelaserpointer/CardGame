package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.entity.CrashReports;

import java.util.List;

public interface CrashReportsDao {
    CrashReports getOne(Integer id);
    void addNew(String reportsContent);
    List<CrashReports> getCrashReportsWithinHalfYear();
    List<CrashReports> getCrashReportsWithinOneDay();
}
