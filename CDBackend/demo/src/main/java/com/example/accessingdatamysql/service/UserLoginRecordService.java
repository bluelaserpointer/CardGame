package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.UserLoginRecord;

import java.util.List;

public interface UserLoginRecordService {
    void userLogin(Integer userId);
    void userLogout(Integer userId, Integer type);
    List<UserLoginRecord> getAllUserLoginRecords();
    List<UserLoginRecord> getUserLoginRecordsByUserId(Integer userId);
}
