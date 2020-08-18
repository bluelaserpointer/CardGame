package com.example.accessingdatamysql.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.PveRecordDao;
import com.example.accessingdatamysql.entity.ListRequest;
import com.example.accessingdatamysql.entity.PveRecord;
import com.example.accessingdatamysql.service.PveRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class PveRecordServiceImpl implements PveRecordService {
    @Autowired
    private PveRecordDao pveRecordDao;

    @Override
    public PveRecord addPveRecord(Integer userId, Integer chapterId, Integer phaseId, Integer result,
            String posRecordStr) {
        return pveRecordDao.addPveRecord(userId, chapterId, phaseId, result, posRecordStr);
    }

    public PveRecord updatePveRecord(Integer pveRecordId, Integer userId, Integer chapterId, Integer phaseId,
            Integer result, Timestamp recordTime, String posRecord) {
        return pveRecordDao.updatePveRecord(pveRecordId, userId, chapterId, phaseId, result, recordTime, posRecord);
    }

    public List<PveRecord> getAllPveRecords() {
        return pveRecordDao.getAllPveRecords();
    }

    public Map<String, Integer> getPveRecordStatistics() {
        return pveRecordDao.getPveRecordStatistics();
    }

    public List<PveRecord> getAllPveRecordsByUser(Integer userId) {
        return pveRecordDao.getAllPveRecordsByUser(userId);
    }

    public boolean deleteAllPveRecordsByUser(Integer userId) {
        return pveRecordDao.deleteAllPveRecordsByUser(userId);
    }

    public boolean deletePveRecords(List<Integer> pveRecordIds) {
        return pveRecordDao.deletePveRecords(pveRecordIds);
    }

    public List<List<Number>> getPveRecordsWithinHalfYear() {
        return pveRecordDao.getPveRecordsWithinHalfYear();
    }

    public List<List<Number>> getPveRecordsWithinOneDay() {
        return pveRecordDao.getPveRecordsWithinOneDay();
    }

    public Integer getPveRecordCountWithinOneDay() {
        return pveRecordDao.getPveRecordCountWithinOneDay();
    }

    @Override
    public JSONObject ListPage(ListRequest listRequest) {
        return pveRecordDao.ListPage(listRequest.getPageToken(), listRequest.getPageSize());
    }
}
