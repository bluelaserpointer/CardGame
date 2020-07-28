package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.UserLoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLoginRecordRepository extends JpaRepository<UserLoginRecord, Integer> {
    UserLoginRecord findUserLoginRecordByUserIdEqualsAndLogoutTimeIsNullOrderByUserLoginRecord(Integer userId);
    List<UserLoginRecord> findUserLoginRecordsByUserIdEquals(Integer userId);
}
