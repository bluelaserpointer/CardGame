package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.Classes.PaginationDao;
import com.example.accessingdatamysql.entity.CrashReports;

import java.util.List;

public interface CrashReportsDao extends PaginationDao {
    CrashReports getOne(Integer id);

    void addNew(Double clientVersion, Integer userId, String stackTrace, String deviceInfo);

    List<CrashReports> getCrashReportsWithinHalfYear();

    List<CrashReports> getCrashReportsWithinOneDay();
}
