package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.OnlineCountRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface OnlineCountRecordRepository extends JpaRepository<OnlineCountRecord, Timestamp> {
    List<OnlineCountRecord> findOnlineCountRecordsByRecordTimeGreaterThanEqualAndRecordTimeLessThanEqual(Timestamp start, Timestamp end);

    @Transactional
    @Query(value = "select * from online_count_record where (substr(record_time, 15, 2) = '30' or substr(record_time, 15, 2) = '00') and record_time > DATE_SUB(NOW(),INTERVAL 6 MONTH)", nativeQuery = true)
    List<OnlineCountRecord> findOnlineCountRecordsWithinHalfYear();

    @Transactional
    @Query(value = "select * from online_count_record where record_time > DATE_SUB(NOW(),INTERVAL 1 DAY)", nativeQuery = true)
    List<OnlineCountRecord> findOnlineCountRecordsWithinOneDay();

    @Transactional
    @Query(value = "select * from online_count_record order by record_time desc limit 1", nativeQuery = true)
    OnlineCountRecord findLatestOnlineCountRecord();
}
