package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.Classes.PaginationDao;
import com.example.accessingdatamysql.entity.CrashReports;

import java.util.List;

public interface CrashReportsDao extends PaginationDao {
    CrashReports getOne(Integer id);

    void addNew(String reportsContent);

    List<CrashReports> getCrashReportsWithinHalfYear();

    List<CrashReports> getCrashReportsWithinOneDay();
}
