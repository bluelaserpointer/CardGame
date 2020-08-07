package com.example.accessingdatamysql.daoimpl;

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
    public void addNew(String reportsContent) {
        final CrashReports reports = new CrashReports();
        reports.setRecordTime(new Timestamp(System.currentTimeMillis()));
        crashReportsRepository.save(reports);
        System.out.println("CrashReportsDaoImpl: saving: " + reportsContent + " at id " + reports.getReportId());
        crashReportsDetailRepository.save(new CrashReportsDetail(reports.getReportId(), reportsContent));
    }

    public List<CrashReports> getCrashReportsWithinHalfYear(){
//        return null;
        List<CrashReports> crashReports = crashReportsRepository.findCrashReportsWithinHalfYear();
        for(int i = 0; i < crashReports.size(); i++)
        {
            CrashReports report = crashReports.get(i);
            crashReportsDetailRepository.findCrashReportsDetailByReportIdEquals(report.getReportId()).ifPresent(report::setDetail);
        }
        return crashReports;
    }

    public List<CrashReports> getCrashReportsWithinOneDay(){
//        return null;
        List<CrashReports> crashReports = crashReportsRepository.findCrashReportsWithinOneDay();
        for(int i = 0; i < crashReports.size(); i++)
        {
            CrashReports report = crashReports.get(i);
            crashReportsDetailRepository.findCrashReportsDetailByReportIdEquals(report.getReportId()).ifPresent(report::setDetail);
        }
        return crashReports;
    }
}
