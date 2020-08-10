package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
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
    public void saveCount() {
        OnlineCountRecord onlineCountRecord = new OnlineCountRecord(onlineCounter.getOnlineCount(), 100);
        onlineCountRecordRepository.save(onlineCountRecord);
    }

    public OnlineCountRecord getOnlineCountRecord() {
        return onlineCountRecordRepository.findLatestOnlineCountRecord();
    }

    public List<OnlineCountRecord> getAllOnlineCountRecords() {
        return onlineCountRecordRepository.findAll();
    }

    public List<OnlineCountRecord> getOnlineCountRecordByRange(Timestamp start, Timestamp end) {
        return onlineCountRecordRepository
                .findOnlineCountRecordsByRecordTimeGreaterThanEqualAndRecordTimeLessThanEqual(start, end);
    }

    public List<OnlineCountRecord> getOnlineCountRecordsWithinHalfYear() {
        return onlineCountRecordRepository.findOnlineCountRecordsWithinHalfYear();
    }

    public List<OnlineCountRecord> getOnlineCountRecordsWithinOneDay() {
        return onlineCountRecordRepository.findOnlineCountRecordsWithinOneDay();
    }

    @Override
    public JSONObject ListPage(final Integer page_token, final Integer page_size) {
        JSONObject response = new JSONObject();

        // get the result data
        Integer start = (page_token - 1) * page_size;
        // Integer end = page_token * page_size - 1;
        List<OnlineCountRecord> onlineCountRecords = onlineCountRecordRepository.ListPage(start, page_size);

        // get the nextPageToken
        Integer nextPageToken;
        if ((onlineCountRecordRepository.findAll().size() - (page_token * page_size)) <= 0) {
            response.put("nextPageToken", "");
        } else {
            nextPageToken = page_token + 1;
            response.put("nextPageToken", nextPageToken);
        }

        // get the total pages of the result
        Integer totalPages = onlineCountRecordRepository.findAll().size() / page_size;
        if ((totalPages - page_size * totalPages) > 0) {
            totalPages += 1;
        }
        // totalPages = totalPages + 1;

        response.put("result", onlineCountRecords);
        response.put("totalPages", totalPages);

        return response;
    }
}
