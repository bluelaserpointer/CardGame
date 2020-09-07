package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.GlobalConstants;
import com.example.accessingdatamysql.dao.CrashReportsDao;
import com.example.accessingdatamysql.entity.CrashReports;
import com.example.accessingdatamysql.entity.CrashReportsDetail;
import com.example.accessingdatamysql.repository.CrashReportsDetailRepository;
import com.example.accessingdatamysql.repository.CrashReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class CrashReportsDaoImpl implements CrashReportsDao {
    @Autowired
    private CrashReportsRepository crashReportsRepository;
    @Autowired
    private CrashReportsDetailRepository crashReportsDetailRepository;

    @Override
    public CrashReports getOne(Integer id) {
        final CrashReports reports = crashReportsRepository.getOne(id);
        crashReportsDetailRepository.findCrashReportsDetailByReportIdEquals(id).ifPresent(reports::setDetail);
        return reports;
    }

    @Override
    public void addNew(Double clientVersion, Integer userId, String stackTrace, String deviceInfo) {
        final CrashReports reports = new CrashReports();
        reports.setRecordTime(new Timestamp(System.currentTimeMillis()));
        reports.setClientVersion(clientVersion);
        reports.setUserId(userId);
        crashReportsRepository.save(reports);
        GlobalConstants.printIfDoDebug("(Hey don't worry, this is client-side bug reports!)");
        GlobalConstants.printIfDoDebug("CrashReportsDaoImpl: saving: " + stackTrace + " at id " + reports.getReportId());
        crashReportsDetailRepository.save(new CrashReportsDetail(reports.getReportId(), stackTrace, deviceInfo));
    }

    public List<CrashReports> getCrashReportsWithinHalfYear() {
        // return null;
        List<CrashReports> crashReports = crashReportsRepository.findCrashReportsWithinHalfYear();
        for (CrashReports report : crashReports) {
            crashReportsDetailRepository.findCrashReportsDetailByReportIdEquals(report.getReportId())
                    .ifPresent(report::setDetail);
        }
        return crashReports;
    }

    public List<CrashReports> getCrashReportsWithinOneDay() {
        // return null;
        List<CrashReports> crashReports = crashReportsRepository.findCrashReportsWithinOneDay();
        for (CrashReports report : crashReports) {
            crashReportsDetailRepository.findCrashReportsDetailByReportIdEquals(report.getReportId())
                    .ifPresent(report::setDetail);
        }
        return crashReports;
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        return this.ListPage(page_token, page_size, crashReportsRepository, crashReport -> {
            crashReportsDetailRepository
                    .findCrashReportsDetailByReportIdEquals(crashReport.getReportId())
                    .ifPresent(crashReport::setDetail);
            return crashReport;
        });
    }
}
