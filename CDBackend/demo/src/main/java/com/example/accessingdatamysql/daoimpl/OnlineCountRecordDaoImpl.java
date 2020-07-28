package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.Security.OnlineCounter;
import com.example.accessingdatamysql.dao.OnlineCountRecordDao;
import com.example.accessingdatamysql.entity.OnlineCountRecord;
import com.example.accessingdatamysql.repository.OnlineCountRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class OnlineCountRecordDaoImpl implements OnlineCountRecordDao {
    @Autowired
    OnlineCountRecordRepository onlineCountRecordRepository;

    @Autowired
    OnlineCounter onlineCounter;

    @Override
    public void saveCount()
    {
        OnlineCountRecord onlineCountRecord = new OnlineCountRecord(onlineCounter.getOnlineCount(), 100);
        onlineCountRecordRepository.save(onlineCountRecord);
    }

    public List<OnlineCountRecord> getAllOnlineCountRecords()
    {
        return onlineCountRecordRepository.findAll();
    }

    public List<OnlineCountRecord> getOnlineCountRecordByRange(Timestamp start, Timestamp end)
    {
        return onlineCountRecordRepository.findOnlineCountRecordsByRecordTimeGreaterThanEqualAndRecordTimeLessThanEqual(start, end);
    }
}
