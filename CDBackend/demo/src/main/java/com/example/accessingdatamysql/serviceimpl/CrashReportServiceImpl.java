package com.example.accessingdatamysql.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.CrashReportsDao;
import com.example.accessingdatamysql.entity.CrashReports;
import com.example.accessingdatamysql.entity.ListRequest;
import com.example.accessingdatamysql.service.CrashReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrashReportServiceImpl implements CrashReportService {
    @Autowired
    private CrashReportsDao crashReportsDao;

    @Override
    public CrashReports getOne(Integer reportId) {
        return crashReportsDao.getOne(reportId);
    }

    @Override
    public void addNew(Double clientVersion, Integer userId, String stackTrace, String deviceInfo) {
        crashReportsDao.addNew(clientVersion, userId, stackTrace, deviceInfo);
    }

    public List<CrashReports> getCrashReportsWithinHalfYear() {
        return crashReportsDao.getCrashReportsWithinHalfYear();
    }

    public List<CrashReports> getCrashReportsWithinOneDay() {
        return crashReportsDao.getCrashReportsWithinOneDay();
    }

    @Override
    public JSONObject ListPage(ListRequest listRequest) {
        return crashReportsDao.ListPage(listRequest.getPageToken(), listRequest.getPageSize());
    }
}
