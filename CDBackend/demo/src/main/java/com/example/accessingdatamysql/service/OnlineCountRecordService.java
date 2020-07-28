package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.OnlineCountRecord;

import java.sql.Timestamp;
import java.util.List;

public interface OnlineCountRecordService {
    void saveCount();

    List<OnlineCountRecord> getAllOnlineCountRecords();

    List<OnlineCountRecord> getOnlineCountRecordByRange(Timestamp start, Timestamp end);
}
