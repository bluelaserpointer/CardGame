package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.*;

import java.util.List;

public interface UserService {
    // 获取一个用户信息
    User getOneUser(Integer UserId);

    // 添加一个新用户
    User addNewUser(User newUser);

    // 更新一个用户信息
    User updateUser(User updateUser);

    // // 只是一个更新的wrapper
    // User updateUserByUser(Integer userId, User user);

    // 获取所有用户信息
    List<User> getAllUsers();

    // 获取指定页数的User
    List<User> ListPage(ListRequest listRequest);

    // 删除部分用户
    String deleteUsers(List<Integer> UserIds);

    // 删除所有用户
    String deleteAll();

    // 确认用户身份
    Integer identifyUser(String userName, String password);

    // 用户查找
    User getOneUserByUserName(String userName);

    // 删除一个指定用户
    List<User> deleteUser(Integer userId);

    // 增加用户经验值(如果累计经验值超过升级所需经验值则升级后再返回user)
    User addExp(Integer userId, Integer exp);

    // 计算用户升级所需经验值
    Integer expToLevelUp(Integer userLevel);
}
