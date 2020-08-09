package com.example.accessingdatamysql.dao;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.PveRecord;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface PveRecordDao {
    PveRecord addPveRecord(Integer userId, Integer chapterId, Integer phaseId, Integer result, String posRecord);

    PveRecord updatePveRecord(Integer pveRecordId, Integer userId, Integer chapterId, Integer phaseId, Integer result,
            Timestamp recordTime, String posRecord);

    // 获取指定页数的用户
    JSONObject ListPage(Integer page_token, Integer page_size);

    List<PveRecord> getAllPveRecords();

    Map<String, Integer> getPveRecordStatistics();

    List<PveRecord> getAllPveRecordsByUser(Integer userId);

    boolean deleteAllPveRecordsByUser(Integer userId);

    boolean deletePveRecords(List<Integer> pveRecordIds);

    List<List<Number>> getPveRecordsWithinHalfYear();

    List<List<Number>> getPveRecordsWithinOneDay();

    Integer getPveRecordCountWithinOneDay();
}
