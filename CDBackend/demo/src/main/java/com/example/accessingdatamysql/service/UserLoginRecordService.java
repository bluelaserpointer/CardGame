package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.UserLoginRecord;

import java.sql.Timestamp;
import java.util.List;

public interface UserLoginRecordService {
    void userLogin(Integer userId);
    void userLogout(Integer userId, Integer type);
    void userAbort(Integer userId, Timestamp logoutTime);
    List<UserLoginRecord> getAllUserLoginRecords();
    List<UserLoginRecord> getUserLoginRecordsByUserId(Integer userId);
    void calculateLogout();
}
