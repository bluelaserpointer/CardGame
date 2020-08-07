package com.example.accessingdatamysql.serviceimpl;

import com.alibaba.fastjson.JSONObject;
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

    // 获取一个用户信息
    @Override
    public User getOneUser(Integer UserId) {
        return userDao.getOneUser(UserId);
    }

    // 添加一个新用户
    @Override
    public User addNewUser(User newUser) {
        return userDao.addNewUser(newUser);
    }

    // 更新一个用户信息
    @Override
    public User updateUser(User updateUser) {
        return userDao.updateUser(updateUser);
    }

    // 只是一个更新的wrapper
    // @Override
    // public User updateUserByUser(Integer userId, User user) {
    // userDao.updateUser(userId, user.getUserName(), user.getEmail(),
    // user.getPassword(), user.getPhoneNumber(),
    // user.getCredits(), user.getAccess(), user.getLevel(), user.getCurExpPoint(),
    // user.getStamina(),
    // user.getMoney(), user.getGrade(), user.getEngKnowledge(),
    // user.getMathKnowledge(),
    // user.getChiKnowledge(), user.getIdentity());
    // return user;
    // }

    // 获取所有用户信息
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    // 删除部分用户
    @Override
    public String deleteUsers(List<Integer> UserIds) {
        return userDao.deleteUsers(UserIds);
    }

    // 删除所有用户
    @Override
    public String deleteAll() {
        return userDao.deleteAll();
    }

    // 登录逻辑
    @Override
    public Integer identifyUser(String userName, String password) {
        User fetchUser = userDao.getOneUserByUserName(userName);
        System.out.println("Identify User:");
        System.out.println(fetchUser);
        if (fetchUser == null) {
            return -1; // 不存在用户
        }
        if (!password.equals(fetchUser.getPassword())) {
            return -2; // 密码不正确
        }

        return fetchUser.getUserId();
    }

    // 删除一个指定用户
    @Override
    public List<User> deleteUser(Integer userId) {
        return userDao.deleteUser(userId);
    }

    // 增加用户经验值(如果累计经验值超过升级所需经验值则升级后再返回user)
    @Override
    public User addExp(Integer userId, Integer exp) {
        User user = userDao.getOneUser(userId);
        Integer expToNextLevel = expToLevelUp(user.getLevel());
        System.out.println("UserService -> addExp:");
        System.out.println("Before Modification: ");
        System.out.println("User: userId = " + userId + " userName = " + user.getUserName() + " level = "
                + user.getLevel() + " curExpPoint = " + user.getCurExpPoint() + " exp = " + exp);
        System.out.println("expToNextLevel: " + expToNextLevel);
        Integer newExp = user.getCurExpPoint() + exp;
        if (newExp > expToNextLevel) {
            newExp -= expToNextLevel;
            user.setLevel(user.getLevel() + 1);
        }
        user.setCurExpPoint(newExp);
        updateUser(user);
        System.out.println("After Modification: ");
        System.out.println("User: userId = " + userId + " userName = " + user.getUserName() + " level = "
                + user.getLevel() + " curExpPoint = " + user.getCurExpPoint());

        return user;
    }

    /*
     * 计算用户升级所需经验值 第一次升要100经验值,每一级比上一级多5%
     */
    @Override
    public Integer expToLevelUp(Integer userLevel) {
        Integer base = 100; // 等级为1时需要100经验值
        // if (userLevel.equals(1))
        // return base;
        double IncreaseRate = 1.05;
        double result = Math.round(Math.pow(IncreaseRate, userLevel - 1) * base); // 小数采用四舍五入法
        System.out.println(result);
        return (int) result;
    }

    // 用户名查找用户
    @Override
    public User getOneUserByUserName(String userName) {
        return userDao.getOneUserByUserName(userName);
    }

    @Override
    public JSONObject ListPage(ListRequest listRequest) {
        return userDao.ListPage(listRequest.getPageToken(), listRequest.getPageSize());
    }

}
