package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.CrashReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CrashReportsRepository extends JpaRepository<CrashReports, Integer> {
    @Transactional
    @Query(value = "select * from crash_reports where cast(substr(record_time, 15, 2) as signed) <= 30 and record_time > DATE_SUB(NOW(),INTERVAL 6 MONTH) order by record_time", nativeQuery = true)
    List<CrashReports> findCrashReportsWithinHalfYear();

    @Transactional
    @Query(value = "select * from crash_reports where record_time > DATE_SUB(NOW(),INTERVAL 1 DAY) order by record_time", nativeQuery = true)
    List<CrashReports> findCrashReportsWithinOneDay();
}
