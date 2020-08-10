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
        final UserLoginRecord userLoginRecord = new UserLoginRecord(userId);
        userLoginRecordRepository.save(userLoginRecord);
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
        JSONObject response = new JSONObject();

        // get the result data
        Integer start = (page_token - 1) * page_size;
        // Integer end = page_token * page_size - 1;
        List<UserLoginRecord> users = userLoginRecordRepository.ListPage(start, page_size);

        // get the nextPageToken
        Integer nextPageToken;
        if ((userLoginRecordRepository.findAll().size() - (page_token * page_size)) <= 0) {
            response.put("nextPageToken", "");
        } else {
            nextPageToken = page_token + 1;
            response.put("nextPageToken", nextPageToken);
        }

        // get the total pages of the result
        Integer totalPages = userLoginRecordRepository.findAll().size() / page_size;
        if ((totalPages - page_size * totalPages) > 0) {
            totalPages += 1;
        }
        // totalPages = totalPages + 1;

        response.put("result", users);
        response.put("totalPages", totalPages);

        return response;
    }
}
