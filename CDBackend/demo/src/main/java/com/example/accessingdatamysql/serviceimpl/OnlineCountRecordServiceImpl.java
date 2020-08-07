package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.OnlineCountRecordDao;
import com.example.accessingdatamysql.entity.OnlineCountRecord;
import com.example.accessingdatamysql.service.OnlineCountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OnlineCountRecordServiceImpl implements OnlineCountRecordService {
    @Autowired
    OnlineCountRecordDao onlineCountRecordDao;

    @Override
    public void saveCount(){
        onlineCountRecordDao.saveCount();
    }

    public OnlineCountRecord getOnlineCountRecord(){
        return onlineCountRecordDao.getOnlineCountRecord();
    }

    public List<OnlineCountRecord> getAllOnlineCountRecords(){
        return onlineCountRecordDao.getAllOnlineCountRecords();
    }

    public List<OnlineCountRecord> getOnlineCountRecordByRange(Timestamp start, Timestamp end)
    {
        return onlineCountRecordDao.getOnlineCountRecordByRange(start, end);
    }

    public List<OnlineCountRecord> getOnlineCountRecordsWithinHalfYear(){
        return onlineCountRecordDao.getOnlineCountRecordsWithinHalfYear();
    }

    public List<OnlineCountRecord> getOnlineCountRecordsWithinOneDay(){
        return onlineCountRecordDao.getOnlineCountRecordsWithinOneDay();
    }
}
