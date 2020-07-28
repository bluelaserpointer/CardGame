package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.CrashReportsDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CrashReportsDetailRepository extends MongoRepository<CrashReportsDetail, Integer> {
    Optional<CrashReportsDetail> findCrashReportsDetailByReportIdEquals(Integer reportId);
}
