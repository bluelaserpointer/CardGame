package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.CrashReportsDao;
import com.example.accessingdatamysql.entity.CrashReports;
import com.example.accessingdatamysql.service.CrashReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrashReportServiceImpl implements CrashReportService {
    @Autowired
    private CrashReportsDao crashReportsDao;

    @Override
    public CrashReports getOne(Integer reportId) {
        return crashReportsDao.getOne(reportId);
    }

    @Override
    public void addNew(String reportContent) {
        crashReportsDao.addNew(reportContent);
    }
}
