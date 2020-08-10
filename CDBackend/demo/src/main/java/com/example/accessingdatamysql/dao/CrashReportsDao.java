package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.Classes.DaoPagination;
import com.example.accessingdatamysql.entity.CrashReports;

import java.util.List;

public interface CrashReportsDao extends DaoPagination {
    CrashReports getOne(Integer id);

    void addNew(String reportsContent);

    List<CrashReports> getCrashReportsWithinHalfYear();

    List<CrashReports> getCrashReportsWithinOneDay();
}
