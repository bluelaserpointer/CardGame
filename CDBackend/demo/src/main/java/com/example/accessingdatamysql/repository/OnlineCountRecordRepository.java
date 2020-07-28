package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.OnlineCountRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface OnlineCountRecordRepository extends JpaRepository<OnlineCountRecord, Timestamp> {
    List<OnlineCountRecord> findOnlineCountRecordsByRecordTimeGreaterThanEqualAndRecordTimeLessThanEqual(Timestamp start, Timestamp end);

}
