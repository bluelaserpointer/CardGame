package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.PveRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface PveRecordService {
    PveRecord addPveRecord(Integer userId, Integer chapterId, Integer phaseId, Integer result, String posRecord)
            throws JsonProcessingException;

    PveRecord updatePveRecord(Integer pveRecordId, Integer userId, Integer chapterId, Integer phaseId, Integer result,
            Timestamp recordTime, String posRecord) throws JsonProcessingException;

    List<PveRecord> getAllPveRecords();

    Map<String, Integer> getPveRecordStatistics();

    List<PveRecord> getAllPveRecordsByUser(Integer userId);

    boolean deleteAllPveRecordsByUser(Integer userId);

    boolean deletePveRecords(List<Integer> pveRecordIds);
}
