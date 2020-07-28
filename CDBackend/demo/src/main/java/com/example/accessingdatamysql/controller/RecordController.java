package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.Security.OnlineCounter;
import com.example.accessingdatamysql.entity.OnlineCountRecord;
import com.example.accessingdatamysql.entity.PveRecord;
import com.example.accessingdatamysql.entity.UserLoginRecord;
import com.example.accessingdatamysql.service.OnlineCountRecordService;
import com.example.accessingdatamysql.service.PveRecordService;
import com.example.accessingdatamysql.service.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/record") // This means URL's start with /demo (after Application path)
public class RecordController {
    @Autowired // This means to get the bean called OwnItemRepository
    private PveRecordService pveRecordService;

    @Autowired // This means to get the bean called OwnItemRepository
    private OnlineCountRecordService onlineCountRecordService;

    @Autowired // This means to get the bean called OwnItemRepository
    private UserLoginRecordService userLoginRecordService;

    @Autowired
    private OnlineCounter onlineCounter;

    @RequestMapping(value = "/pveRecord/getAllPveRecords")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<PveRecord> getAllPveRecords() {
        return pveRecordService.getAllPveRecords();
    }

    @RequestMapping(value = "/pveRecord/getPveRecordStatistics")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Map<String, Integer> getPveRecordStatistics() {
        return pveRecordService.getPveRecordStatistics();
    }

    @RequestMapping(value = "/pveRecord/getAllPveRecordsByUser")
    public List<PveRecord> getAllPveRecordsByUser(@RequestParam("userId") Integer userId) {
        return pveRecordService.getAllPveRecordsByUser(userId);
    }

    @RequestMapping(value = "/onlineCountRecord/getAllOnlineCountRecords")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OnlineCountRecord> getAllOnlineCountRecords() {
        return onlineCountRecordService.getAllOnlineCountRecords();
    }

    @RequestMapping(value = "/onlineCountRecord/getOnlineCountRecordByRange")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OnlineCountRecord> getOnlineCountRecordByRange(@RequestParam("start")Timestamp start, @RequestParam("end")Timestamp end) {
        return onlineCountRecordService.getOnlineCountRecordByRange(start, end);
    }

    @RequestMapping(value = "/userLoginRecord/getAllUserLoginRecords")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserLoginRecord> getAllUserLoginRecords() {
        return userLoginRecordService.getAllUserLoginRecords();
    }

    @RequestMapping(value = "/userLoginRecord/getUserLoginRecordsByUserId")
    public List<UserLoginRecord> getUserLoginRecordsByUserId(@RequestParam("userId")Integer userId) {
        return userLoginRecordService.getUserLoginRecordsByUserId(userId);
    }

    // 获取当前在线人数
    @RequestMapping(value = "/getOnlineCount")
    public int getRealOnlineCount(){
        return onlineCounter.getOnlineCount();
    }
}
