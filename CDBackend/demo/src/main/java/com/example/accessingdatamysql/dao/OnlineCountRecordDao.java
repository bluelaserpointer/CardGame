package com.example.accessingdatamysql.dao;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.OnlineCountRecord;

import java.sql.Timestamp;
import java.util.List;

public interface OnlineCountRecordDao {
    void saveCount();

    OnlineCountRecord getOnlineCountRecord();

    // 获取指定页数的用户
    JSONObject ListPage(Integer page_token, Integer page_size);

    List<OnlineCountRecord> getAllOnlineCountRecords();

    List<OnlineCountRecord> getOnlineCountRecordByRange(Timestamp start, Timestamp end);

    List<OnlineCountRecord> getOnlineCountRecordsWithinHalfYear();

    List<OnlineCountRecord> getOnlineCountRecordsWithinOneDay();

}
