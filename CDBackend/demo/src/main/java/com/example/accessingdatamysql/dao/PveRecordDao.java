package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.entity.PveRecord;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface PveRecordDao {
    PveRecord addPveRecord(PveRecord newPveRecord) throws JsonProcessingException;

    PveRecord updatePveRecord(PveRecord updatePveRecord) throws JsonProcessingException;

    List<PveRecord> getAllPveRecords();

    Map<String, Integer> getPveRecordStatistics();

    List<PveRecord> getAllPveRecordsByUser(Integer userId);

    boolean deleteAllPveRecordsByUser(Integer userId);

    boolean deletePveRecords(List<Integer> pveRecordIds);
}
