package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getOneUser(Integer UserId) {
        return userDao.getOneUser(UserId);
    }

    public String addNewUser(String userName, String email, String password, String phoneNumber) {
        return userDao.addNewUser(userName, email, password, phoneNumber);
    }

    public String updateUser(Integer UserId, String userName, String email, String password, String phoneNumber,
            Integer credits, Boolean access, Integer level) {
        return userDao.updateUser(UserId, userName, email, password, phoneNumber, credits, access, level);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public String deleteUsers(List<Integer> UserIds) {
        return userDao.deleteUsers(UserIds);
    }

    public String deleteAll() {
        return userDao.deleteAll();
    }

    public boolean identifyUser(String userName, String password){
        User fetchUser = userDao.getOneUserByUserName(userName);
        if(fetchUser != null)
        {
            return password.equals(fetchUser.getPassword());
        }
        return false;
    }

    public List<User> deleteUser(Integer userId) {
        return userDao.deleteUser(userId);
    }

}
