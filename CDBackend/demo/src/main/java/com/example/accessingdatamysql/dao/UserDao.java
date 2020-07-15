package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface UserDao {
        // 获取一个用户信息
        User getOneUser(Integer UserId);

        // 添加一个新用户
        String addNewUser(String userName, String email, String password, String phoneNumber);

        // 更新一个用户信息
        String updateUser(Integer UserId, String userName, String email, String password, String phoneNumber,
                        Integer credits, Boolean access, Integer level, Integer curExpPoint, Integer stamina,
                        Integer money, Double grade, Integer engKnowledge, Integer mathKnowledge, Integer chiKnowledge);

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
