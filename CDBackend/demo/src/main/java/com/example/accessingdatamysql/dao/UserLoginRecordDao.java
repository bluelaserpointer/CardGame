package com.example.accessingdatamysql.dao;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.UserLoginRecord;

import java.sql.Timestamp;
import java.util.List;

public interface UserLoginRecordDao {
    void userLogin(Integer userId);

    void userLogout(Integer userId, Integer type, Timestamp logoutTime);

    // 获取指定页数的用户
    JSONObject ListPage(Integer page_token, Integer page_size);

    List<UserLoginRecord> getAllUserLoginRecords();

    List<UserLoginRecord> getUserLoginRecordsByUserId(Integer userId);

    List<UserLoginRecord> getUserLoginRecordsByExpiration();
}
