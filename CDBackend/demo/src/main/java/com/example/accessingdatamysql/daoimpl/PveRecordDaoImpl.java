package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.PveRecordDao;
import com.example.accessingdatamysql.entity.PveRecord;
import com.example.accessingdatamysql.repository.PveRecordRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PveRecordDaoImpl implements PveRecordDao {
    @Autowired
    private PveRecordRepository pveRecordRepository;

    // 将 String类型的 "[[2,1], [3,2], [4,3]]" 等 List<List> 形式转换为 Map
    public Map<Integer, Integer> parsePosRecord(String awardItems) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            map = mapper.readValue(awardItems, new TypeReference<HashMap<Integer, Integer>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public PveRecord addPveRecord(Integer userId, Integer chapterId, Integer phaseId, Integer result,
            String posRecord) {
        PveRecord pveRecord = new PveRecord(userId, chapterId, phaseId, result);
        pveRecord.setPosRecord(parsePosRecord(posRecord));
        pveRecord.setRecordTime(new Timestamp(System.currentTimeMillis()));

        return pveRecordRepository.save(pveRecord);
    }

    @Override
    public PveRecord updatePveRecord(Integer pveRecordId, Integer userId, Integer chapterId, Integer phaseId,
            Integer result, Timestamp recordTime, String posRecord) {
        Optional<PveRecord> optPveRecord = pveRecordRepository.findById(pveRecordId);

        if (optPveRecord.isPresent()) {
            PveRecord pveRecord = optPveRecord.get();
            pveRecord.setPveRecord(userId, chapterId, phaseId, result, recordTime);
            pveRecord.setPosRecord(parsePosRecord(posRecord));
            return pveRecordRepository.save(pveRecord);
        }

        return null;
    }

    @Override
    public List<PveRecord> getAllPveRecords() {
        return pveRecordRepository.findAll();
    }

    @Override
    public Map<String, Integer> getPveRecordStatistics() {
        return null;
        // return pveRecordDao.getPveRecordStatistics();
    }

    @Override
    public List<PveRecord> getAllPveRecordsByUser(Integer userId) {
        return pveRecordRepository.findPveRecordsByUserIdEquals(userId);
    }

    @Override
    public boolean deleteAllPveRecordsByUser(Integer userId) {
        return pveRecordRepository.deletePveRecordsByUserIdEquals(userId);
    }

    @Override
    public boolean deletePveRecords(List<Integer> pveRecordIds) {
        for (Integer pveRecordId : pveRecordIds) {
            pveRecordRepository.deleteById(pveRecordId);
        }
        return true;
    }

    @Override
    public List<List<Number>> getPveRecordsWithinHalfYear() {
        return pveRecordRepository.findPveRecordsWithinHalfYear();
    }

    @Override
    public List<List<Number>> getPveRecordsWithinOneDay() {
        return pveRecordRepository.findPveRecordsWithinOneDay();
    }

    @Override
    public Integer getPveRecordCountWithinOneDay() {
        return pveRecordRepository.findPveRecordCountWithinOneDay();
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        JSONObject response = new JSONObject();

        // get the result data
        Integer start = (page_token - 1) * page_size;
        // Integer end = page_token * page_size - 1;
        List<PveRecord> pveRecords = pveRecordRepository.ListPage(start, page_size);

        // get the nextPageToken
        Integer nextPageToken;
        if ((pveRecordRepository.findAll().size() - (page_token * page_size)) <= 0) {
            response.put("nextPageToken", "");
        } else {
            nextPageToken = page_token + 1;
            response.put("nextPageToken", nextPageToken);
        }

        // get the total pages of the result
        Integer totalPages = pveRecordRepository.findAll().size() / page_size;
        if ((totalPages - page_size * totalPages) > 0) {
            totalPages += 1;
        }
        // totalPages = totalPages + 1;

        response.put("result", pveRecords);
        response.put("totalPages", totalPages);

        return response;
    }
}
