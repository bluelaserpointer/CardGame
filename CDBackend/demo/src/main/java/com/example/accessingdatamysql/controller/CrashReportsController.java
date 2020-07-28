package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.CrashReports;
import com.example.accessingdatamysql.entity.CrashReportsDetail;
import com.example.accessingdatamysql.service.CrashReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/crashReports") // This means URL's start with /demo (after Application path)
public class CrashReportsController {
    @Autowired
    private CrashReportService crashReportService;

    @PostMapping(value = "/add")
    public @ResponseBody void addCrashReport(@RequestBody CrashReportsDetail crashReportsDetail) {
        System.out.println("CrashReportsController: received content: " + crashReportsDetail.getContent());
        crashReportService.addNew(crashReportsDetail.getContent());
    }

    @GetMapping(value = "/get")
    public @ResponseBody CrashReports getCrashReport(@RequestParam Integer reportId) {
        return crashReportService.getOne(reportId);
    }
}
