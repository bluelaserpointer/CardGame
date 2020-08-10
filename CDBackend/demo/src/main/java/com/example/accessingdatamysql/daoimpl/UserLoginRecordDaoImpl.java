package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.UserLoginRecordDao;
import com.example.accessingdatamysql.entity.UserLoginRecord;
import com.example.accessingdatamysql.repository.UserLoginRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class UserLoginRecordDaoImpl implements UserLoginRecordDao {
    @Autowired
    UserLoginRecordRepository userLoginRecordRepository;

    @Override
    public void userLogin(final Integer userId) {
        userLoginRecordRepository.save(new UserLoginRecord(userId));
    }

    public void userLogout(final Integer userId, final Integer type, final Timestamp logoutTime) {
        final UserLoginRecord userLoginRecord = userLoginRecordRepository
                .findUserLoginRecordByUserIdEqualsAndLogoutTimeIsNullOrderByUserLoginRecordIdAsc(userId).get(0);
        userLoginRecord.setUserLoginRecord(type);
        userLoginRecord.setLogoutTime(logoutTime);
        userLoginRecordRepository.save(userLoginRecord);
    }

    public List<UserLoginRecord> getAllUserLoginRecords() {
        return userLoginRecordRepository.findAll();
    }

    public List<UserLoginRecord> getUserLoginRecordsByUserId(final Integer userId) {
        return userLoginRecordRepository.findUserLoginRecordsByUserIdEquals(userId);
    }

    public List<UserLoginRecord> getUserLoginRecordsByExpiration() {
        return userLoginRecordRepository.findUserLoginRecordsByLogoutTimeIsNullOrderByUserLoginRecordIdAsc();
    }

    @Override
    public JSONObject ListPage(final Integer page_token, final Integer page_size) {
        return this.ListPage(page_token, page_size, userLoginRecordRepository);
    }
}
