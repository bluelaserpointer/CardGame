package com.example.accessingdatamysql.service;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.ListRequest;
import com.example.accessingdatamysql.entity.OnlineCountRecord;

import java.sql.Timestamp;
import java.util.List;

public interface OnlineCountRecordService {
    void saveCount();

    OnlineCountRecord getOnlineCountRecord();

    // 获取指定页数的User
    JSONObject ListPage(ListRequest listRequest);

    List<OnlineCountRecord> getAllOnlineCountRecords();

    List<OnlineCountRecord> getOnlineCountRecordByRange(Timestamp start, Timestamp end);

    List<OnlineCountRecord> getOnlineCountRecordsWithinHalfYear();

    List<OnlineCountRecord> getOnlineCountRecordsWithinOneDay();
}
