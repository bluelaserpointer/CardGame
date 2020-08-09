package com.example.accessingdatamysql.service;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.ListRequest;
import com.example.accessingdatamysql.entity.UserLoginRecord;

import java.sql.Timestamp;
import java.util.List;

public interface UserLoginRecordService {
    void userLogin(Integer userId);

    void userLogout(Integer userId, Integer type);

    void userAbort(Integer userId, Timestamp logoutTime);

    // 获取指定页数的User
    JSONObject ListPage(ListRequest listRequest);

    List<UserLoginRecord> getAllUserLoginRecords();

    List<UserLoginRecord> getUserLoginRecordsByUserId(Integer userId);

    void calculateLogout();
}
