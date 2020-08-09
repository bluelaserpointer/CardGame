package com.example.accessingdatamysql.dao;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.CrashReports;

import java.util.List;

public interface CrashReportsDao {
    CrashReports getOne(Integer id);

    void addNew(String reportsContent);

    // 获取指定页数的用户
    JSONObject ListPage(Integer page_token, Integer page_size);

    List<CrashReports> getCrashReportsWithinHalfYear();

    List<CrashReports> getCrashReportsWithinOneDay();
}
