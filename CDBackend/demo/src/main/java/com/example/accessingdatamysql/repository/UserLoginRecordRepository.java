package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.Classes.PaginationJpaRepository;
import com.example.accessingdatamysql.entity.UserLoginRecord;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import javax.transaction.Transactional;

public interface UserLoginRecordRepository extends PaginationJpaRepository<UserLoginRecord, Integer> {
    List<UserLoginRecord> findUserLoginRecordByUserIdEqualsAndLogoutTimeIsNullOrderByUserLoginRecordIdAsc(
            Integer userId);

    List<UserLoginRecord> findUserLoginRecordsByUserIdEquals(Integer userId);

    List<UserLoginRecord> findUserLoginRecordsByLogoutTimeIsNullOrderByUserLoginRecordIdAsc();

    @Transactional
    @Query(value = "SELECT * from user_login_record LIMIT ?1,?2", nativeQuery = true)
    @Override
    List<UserLoginRecord> ListPage(Integer start, Integer end);
}
