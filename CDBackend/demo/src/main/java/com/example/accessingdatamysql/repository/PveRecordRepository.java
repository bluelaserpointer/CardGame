package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.Classes.ListPagination;
import com.example.accessingdatamysql.entity.PveRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PveRecordRepository extends JpaRepository<PveRecord, Integer>, ListPagination<PveRecord> {
    List<PveRecord> findPveRecordsByUserIdEquals(Integer userId);

    @Transactional
    @Modifying
    boolean deletePveRecordsByUserIdEquals(Integer userId);

    @Transactional
    @Query(value = "select chapter_id, count(*) as count, avg(result) as avgResult from pve_record where record_time > DATE_SUB(NOW(), INTERVAL 6 MONTH) group by chapter_id", nativeQuery = true)
    List<List<Number>> findPveRecordsWithinHalfYear();

    @Transactional
    @Query(value = "select chapter_id, count(*) as count, avg(result) as avgResult from pve_record where record_time > DATE_SUB(NOW(), INTERVAL 1 DAY) group by chapter_id", nativeQuery = true)
    List<List<Number>> findPveRecordsWithinOneDay();

    @Transactional
    @Query(value = "select count(*) from pve_record where record_time > DATE_SUB(NOW(), INTERVAL 1 DAY)", nativeQuery = true)
    Integer findPveRecordCountWithinOneDay();

    @Transactional
    @Query(value = "SELECT * from pve_record LIMIT ?1,?2", nativeQuery = true)
    @Override
    List<PveRecord> ListPage(Integer start, Integer end);
}
