package com.example.accessingdatamysql.daoimpl;

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
    public void userLogin(Integer userId){
        UserLoginRecord userLoginRecord = new UserLoginRecord(userId);
        userLoginRecordRepository.save(userLoginRecord);
    }

    public void userLogout(Integer userId, Integer type, Timestamp logoutTime){
        UserLoginRecord userLoginRecord = userLoginRecordRepository.findUserLoginRecordByUserIdEqualsAndLogoutTimeIsNullOrderByUserLoginRecordIdAsc(userId).get(0);
        userLoginRecord.setUserLoginRecord(type);
        userLoginRecord.setLogoutTime(logoutTime);
        userLoginRecordRepository.save(userLoginRecord);
    }

    public List<UserLoginRecord> getAllUserLoginRecords()
    {
        return userLoginRecordRepository.findAll();
    }

    public List<UserLoginRecord> getUserLoginRecordsByUserId(Integer userId)
    {
        return userLoginRecordRepository.findUserLoginRecordsByUserIdEquals(userId);
    }


    public List<UserLoginRecord> getUserLoginRecordsByExpiration()
    {
        return userLoginRecordRepository.findUserLoginRecordsByLogoutTimeIsNullOrderByUserLoginRecordIdAsc();
    }
}
