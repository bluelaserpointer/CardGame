package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.PveRecordDao;
import com.example.accessingdatamysql.entity.PveRecord;
import com.example.accessingdatamysql.service.PveRecordService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public PveRecord addPveRecord(PveRecord newPveRecord) throws JsonProcessingException {
        return pveRecordDao.addPveRecord(newPveRecord);
    }

    public PveRecord updatePveRecord(PveRecord updatePveRecord) throws JsonProcessingException {
        return pveRecordDao.updatePveRecord(updatePveRecord);
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
}
