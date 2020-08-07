package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.UserLoginRecordDao;
import com.example.accessingdatamysql.entity.UserLoginRecord;
import com.example.accessingdatamysql.service.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.accessingdatamysql.Security.SecurityConstants.EXPIRATION_TIME;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserLoginRecordServiceImpl implements UserLoginRecordService {
    @Autowired
    UserLoginRecordDao userLoginRecordDao;

    @Override
    public void userLogin(Integer userId){
        userLoginRecordDao.userLogin(userId);
    }

    // 用户正常退出，后续可增加退出种类
    public void userLogout(Integer userId, Integer type){
        switch(type){
            case 2:
                userAbort(userId, new Timestamp(System.currentTimeMillis()));
                break;
            case 1:
            default:
                userLoginRecordDao.userLogout(userId, type, new Timestamp(System.currentTimeMillis()));
                break;
        }

    }

    // 用户异常退出，记录效果明显需要调小常数数值
    public void userAbort(Integer userId, Timestamp logoutTime)
    {
        userLoginRecordDao.userLogout(userId, 2, logoutTime);
    }

    public List<UserLoginRecord> getAllUserLoginRecords()
    {
        return userLoginRecordDao.getAllUserLoginRecords();
    }

    public List<UserLoginRecord> getUserLoginRecordsByUserId(Integer userId)
    {
        return userLoginRecordDao.getUserLoginRecordsByUserId(userId);
    }

    // 清理已过期，未正常登出用户的登录记录
    public void calculateLogout(){
        List<UserLoginRecord> userLoginRecords = userLoginRecordDao.getUserLoginRecordsByExpiration();
        long now = System.currentTimeMillis();
        for (UserLoginRecord userLoginRecord : userLoginRecords) {
            long logicalExpirationTime = userLoginRecord.getLoginTime().getTime() + EXPIRATION_TIME;

            // 查看是否当前时间已超该记录正常退出时间
            if (now > logicalExpirationTime) {
                userAbort(userLoginRecord.getUserId(), new Timestamp(logicalExpirationTime));
            }
        }
    }
}
