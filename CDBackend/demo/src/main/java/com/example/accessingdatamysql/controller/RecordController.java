package com.example.accessingdatamysql.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.Security.OnlineCounter;
import com.example.accessingdatamysql.entity.CrashReports;
import com.example.accessingdatamysql.entity.ListRequest;
import com.example.accessingdatamysql.entity.OnlineCountRecord;
import com.example.accessingdatamysql.entity.PveRecord;
import com.example.accessingdatamysql.entity.UserLoginRecord;
import com.example.accessingdatamysql.service.CrashReportService;
import com.example.accessingdatamysql.service.OnlineCountRecordService;
import com.example.accessingdatamysql.service.PveRecordService;
import com.example.accessingdatamysql.service.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import static com.example.accessingdatamysql.GlobalConstants.general_page_size;

@CrossOrigin(origins = "*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/record") // This means URL's start with /demo (after Application path)
public class RecordController {

    @Autowired
    private OnlineCountRecordService onlineCountRecordService;

    @Autowired
    private UserLoginRecordService userLoginRecordService;

    @Autowired
    private PveRecordService pveRecordService;

    @Autowired
    private CrashReportService crashReportService;

    @Autowired
    private OnlineCounter onlineCounter;

    // 获取当前在线人数
    @RequestMapping(value = "/onlineCountRecord/getOnlineCount")
    public int getRealOnlineCount() {
        return onlineCounter.getOnlineCount();
    }

    // 获取当前在线人数日志Entity
    @RequestMapping(value = "/onlineCountRecord/getOnlineCountRecord")
    public OnlineCountRecord getOnlineCountRecord() {
        return onlineCountRecordService.getOnlineCountRecord();
    }

    // 获取所有的在线人数日志
    @RequestMapping(value = "/onlineCountRecord/getAllOnlineCountRecords")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OnlineCountRecord> getAllOnlineCountRecords() {
        return onlineCountRecordService.getAllOnlineCountRecords();
    }

    // 获取一段时间内的在线人数日志
    @RequestMapping(value = "/onlineCountRecord/getOnlineCountRecordByRange")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OnlineCountRecord> getOnlineCountRecordByRange(@RequestParam("start") Timestamp start,
            @RequestParam("end") Timestamp end) {
        return onlineCountRecordService.getOnlineCountRecordByRange(start, end);
    }

    // 获取半年内的在线人数日志
    @RequestMapping(value = "/onlineCountRecord/getOnlineCountRecordsWithinHalfYear")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OnlineCountRecord> getOnlineCountRecordsWithinHalfYear() {
        return onlineCountRecordService.getOnlineCountRecordsWithinHalfYear();
    }

    // 获取一天内的在线人数日志
    @RequestMapping(value = "/onlineCountRecord/getOnlineCountRecordsWithinOneDay")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OnlineCountRecord> getOnlineCountRecordsWithinOneDay() {
        return onlineCountRecordService.getOnlineCountRecordsWithinOneDay();
    }

    // 获取所有的玩家登录日志
    @RequestMapping(value = "/userLoginRecord/getAllUserLoginRecords")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserLoginRecord> getAllUserLoginRecords() {
        return userLoginRecordService.getAllUserLoginRecords();
    }

    // 获取指定页数的数据

    // RecordType 用来指定需要获取的记录类型:
    // - OnlineCountRecord 1
    // - UserLoginRecord 2
    // - PveRecord 3
    // - CrashReport 4

    @RequestMapping(value = "/List")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public JSONObject ListPage(@RequestBody ListRequest ListRequest, @RequestParam Integer recordType) {
        ListRequest.setPageSize(general_page_size);
        final String request = JSON.toJSONString(ListRequest);
        System.out.print(request);
        JSONObject response = new JSONObject();
        switch (recordType) {
            case 1:
                response = onlineCountRecordService.ListPage(ListRequest);
                break;
            case 2:
                response = userLoginRecordService.ListPage(ListRequest);
                break;
            case 3:
                response = pveRecordService.ListPage(ListRequest);
                break;
            case 4:
                response = crashReportService.ListPage(ListRequest);
                break;
        }
        return response;

    }

    // 获取特定的玩家登录日志
    @RequestMapping(value = "/userLoginRecord/getUserLoginRecordsByUserId")
    public List<UserLoginRecord> getUserLoginRecordsByUserId(@RequestParam("userId") Integer userId) {
        return userLoginRecordService.getUserLoginRecordsByUserId(userId);
    }

    // 获取半年内的关卡挑战日志
    @RequestMapping(value = "/pveRecord/getPveRecordsWithinHalfYear")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<List<Number>> getPveRecordsWithinHalfYear() {
        return pveRecordService.getPveRecordsWithinHalfYear();
    }

    // 获取一天内的关卡挑战日志
    @RequestMapping(value = "/pveRecord/getPveRecordsWithinOneDay")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<List<Number>> getPveRecordsWithinOneDay() {
        return pveRecordService.getPveRecordsWithinOneDay();
    }

    // 获取一天内的关卡挑战日志总数
    @RequestMapping(value = "/pveRecord/getPveRecordCountWithinOneDay")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Integer getPveRecordCountWithinOneDay() {
        return pveRecordService.getPveRecordCountWithinOneDay();
    }

    // 获取半年内的系统崩溃日志
    @RequestMapping(value = "/crashReport/getCrashReportsWithinHalfYear")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CrashReports> getCrashReportsWithinHalfYear() {
        return crashReportService.getCrashReportsWithinHalfYear();
    }

    // 获取一天内的系统崩溃日志
    @RequestMapping(value = "/crashReport/getCrashReportsWithinOneDay")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CrashReports> getCrashReportsWithinOneDay() {
        return crashReportService.getCrashReportsWithinOneDay();
    }

}
