package com.example.accessingdatamysql.dao;

import java.util.List;

import com.example.accessingdatamysql.Classes.JSONPagination;
import com.example.accessingdatamysql.entity.*;

public interface UserDao extends JSONPagination {
        // 获取一个用户信息
        User getOneUser(Integer UserId);

        // 添加一个新用户
        User addNewUser(User newUser);

        // 更新一个用户信息
        User updateUser(User updateUser);

        // 获取所有用户信息
        List<User> getAllUsers();

        // 删除部分用户
        String deleteUsers(List<Integer> UserIds);

        // 删除所有用户
        String deleteAll();

        // 使用用户名查询用户
        User getOneUserByUserName(String userName);

        // 删除一个指定用户
        List<User> deleteUser(Integer userId);
}
