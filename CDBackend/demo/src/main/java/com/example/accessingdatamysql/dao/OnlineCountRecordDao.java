package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.entity.OnlineCountRecord;

import java.sql.Timestamp;
import java.util.List;

public interface OnlineCountRecordDao {
    void saveCount();
    List<OnlineCountRecord> getAllOnlineCountRecords();
    List<OnlineCountRecord> getOnlineCountRecordByRange(Timestamp start, Timestamp end);

}
