package com.example.accessingdatamysql.service;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.CrashReports;
import com.example.accessingdatamysql.entity.ListRequest;

import java.util.List;

public interface CrashReportService {
    CrashReports getOne(Integer reportId);

    void addNew(String reportContent);

    // 获取指定页数的User
    JSONObject ListPage(ListRequest listRequest);

    List<CrashReports> getCrashReportsWithinHalfYear();

    List<CrashReports> getCrashReportsWithinOneDay();

}
