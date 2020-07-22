package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.PveRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface PveRecordRepository extends JpaRepository<PveRecord, Integer> {
    List<PveRecord> findPveRecordsByUserIdEquals(Integer userId);

    @Transactional
    @Modifying
    boolean deletePveRecordsByUserIdEquals(Integer userId);
}
