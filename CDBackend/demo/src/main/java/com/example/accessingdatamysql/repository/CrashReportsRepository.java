package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.CrashReports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrashReportsRepository extends JpaRepository<CrashReports, Integer> {
}
