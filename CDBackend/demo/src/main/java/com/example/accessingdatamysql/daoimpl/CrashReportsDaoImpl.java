package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.CrashReportsDao;
import com.example.accessingdatamysql.entity.CrashReports;
import com.example.accessingdatamysql.entity.CrashReportsDetail;
import com.example.accessingdatamysql.repository.CrashReportsDetailRepository;
import com.example.accessingdatamysql.repository.CrashReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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

    public List<CrashReports> getCrashReportsWithinHalfYear() {
        // return null;
        List<CrashReports> crashReports = crashReportsRepository.findCrashReportsWithinHalfYear();
        for (int i = 0; i < crashReports.size(); i++) {
            CrashReports report = crashReports.get(i);
            crashReportsDetailRepository.findCrashReportsDetailByReportIdEquals(report.getReportId())
                    .ifPresent(report::setDetail);
        }
        return crashReports;
    }

    public List<CrashReports> getCrashReportsWithinOneDay() {
        // return null;
        List<CrashReports> crashReports = crashReportsRepository.findCrashReportsWithinOneDay();
        for (int i = 0; i < crashReports.size(); i++) {
            CrashReports report = crashReports.get(i);
            crashReportsDetailRepository.findCrashReportsDetailByReportIdEquals(report.getReportId())
                    .ifPresent(report::setDetail);
        }
        return crashReports;
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        JSONObject response = new JSONObject();

        // get the result data
        Integer start = (page_token - 1) * page_size;
        // Integer end = page_token * page_size - 1;
        List<CrashReports> crashReports = crashReportsRepository.ListPage(start, page_size);
        for (int i = 0; i < crashReports.size(); i++) {
            CrashReports crashReport = crashReports.get(i);
            Optional<CrashReportsDetail> crashReportDetails = crashReportsDetailRepository
                    .findCrashReportsDetailByReportIdEquals(crashReport.getReportId());
            crashReportDetails.ifPresent(crashReport::setDetail);
            crashReports.set(i, crashReport);
        }

        // get the nextPageToken
        Integer nextPageToken;
        if ((crashReportsRepository.findAll().size() - (page_token * page_size)) <= 0) {
            response.put("nextPageToken", "");
        } else {
            nextPageToken = page_token + 1;
            response.put("nextPageToken", nextPageToken);
        }

        // get the total pages of the result
        Integer totalPages = crashReportsRepository.findAll().size() / page_size;
        if ((totalPages - page_size * totalPages) > 0) {
            totalPages += 1;
        }
        response.put("result", crashReports);
        response.put("totalPages", totalPages);

        return response;
    };
}
