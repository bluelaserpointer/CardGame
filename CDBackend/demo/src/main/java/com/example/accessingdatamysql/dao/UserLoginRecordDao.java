package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.Classes.DaoPagination;
import com.example.accessingdatamysql.entity.UserLoginRecord;

import java.sql.Timestamp;
import java.util.List;

public interface UserLoginRecordDao extends DaoPagination {
    void userLogin(Integer userId);

    void userLogout(Integer userId, Integer type, Timestamp logoutTime);

    List<UserLoginRecord> getAllUserLoginRecords();

    List<UserLoginRecord> getUserLoginRecordsByUserId(Integer userId);

    List<UserLoginRecord> getUserLoginRecordsByExpiration();
}
