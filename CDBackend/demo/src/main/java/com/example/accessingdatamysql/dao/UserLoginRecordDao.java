package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.entity.UserLoginRecord;

import java.util.List;

public interface UserLoginRecordDao {
    void userLogin(Integer userId);
    void userLogout(Integer userId, Integer type);
    List<UserLoginRecord> getAllUserLoginRecords();
    List<UserLoginRecord> getUserLoginRecordsByUserId(Integer userId);
}
