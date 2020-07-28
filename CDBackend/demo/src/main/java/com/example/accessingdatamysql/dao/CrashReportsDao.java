package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.entity.CrashReports;

public interface CrashReportsDao {
    CrashReports getOne(Integer id);
    void addNew(String reportsContent);
}
