package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.entity.PveRecord;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface PveRecordDao {
    PveRecord addPveRecord(Integer userId, Integer chapterId, Integer phaseId, Integer result, String posRecord)
            throws JsonProcessingException;

    PveRecord updatePveRecord(Integer pveRecordId, Integer userId, Integer chapterId, Integer phaseId, Integer result,
            Timestamp recordTime, String posRecord) throws JsonProcessingException;

    List<PveRecord> getAllPveRecords();

    Map<String, Integer> getPveRecordStatistics();

    List<PveRecord> getAllPveRecordsByUser(Integer userId);

    boolean deleteAllPveRecordsByUser(Integer userId);

    boolean deletePveRecords(List<Integer> pveRecordIds);

    List<List<Number>> getPveRecordsWithinHalfYear();

    List<List<Number>> getPveRecordsWithinOneDay();

    Integer getPveRecordCountWithinOneDay();
}
