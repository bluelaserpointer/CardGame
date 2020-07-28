package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.UserLoginRecordDao;
import com.example.accessingdatamysql.entity.UserLoginRecord;
import com.example.accessingdatamysql.service.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginRecordServiceImpl implements UserLoginRecordService {
    @Autowired
    UserLoginRecordDao userLoginRecordDao;

    @Override
    public void userLogin(Integer userId){
        userLoginRecordDao.userLogin(userId);
    }

    public void userLogout(Integer userId, Integer type){
        userLoginRecordDao.userLogout(userId, type);
    }

    public List<UserLoginRecord> getAllUserLoginRecords()
    {
        return userLoginRecordDao.getAllUserLoginRecords();
    }

    public List<UserLoginRecord> getUserLoginRecordsByUserId(Integer userId)
    {
        return userLoginRecordDao.getUserLoginRecordsByUserId(userId);
    }
}
