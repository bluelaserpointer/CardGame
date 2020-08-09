package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.PveRecord;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface PveRecordService {
    PveRecord addPveRecord(Integer userId, Integer chapterId, Integer phaseId, Integer result, String posRecord);

    PveRecord updatePveRecord(Integer pveRecordId, Integer userId, Integer chapterId, Integer phaseId, Integer result,
            Timestamp recordTime, String posRecord);

    List<PveRecord> getAllPveRecords();

    Map<String, Integer> getPveRecordStatistics();

    List<PveRecord> getAllPveRecordsByUser(Integer userId);

    boolean deleteAllPveRecordsByUser(Integer userId);

    boolean deletePveRecords(List<Integer> pveRecordIds);

    List<List<Number>> getPveRecordsWithinHalfYear();

    List<List<Number>> getPveRecordsWithinOneDay();

    Integer getPveRecordCountWithinOneDay();
}
