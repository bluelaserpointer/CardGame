package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.UserDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository UserRepository;

    // 获取一个用户信息
    @Override
    public User getOneUser(Integer UserId) {
        return UserRepository.getOne(UserId);
    }

    // 添加一个新用户
    @Override
    public User addNewUser(User newUser) {
        final User user = new User(newUser.getUserName(), newUser.getEmail(), newUser.getPassword(), newUser.getPhoneNumber(),
                newUser.getIdentity());
        // GlobalConstants.printIfDoDebug("new User has an Id of : " + n.getUserId());
        UserRepository.save(user);
        return user;
    }

    // 更新一个用户信息
    @Override
    public User updateUser(User newUser) {
        final User user = UserRepository.getOne(newUser.getUserId());
        // GlobalConstants.printIfDoDebug("old User has an Id of : " + n.getUserId());
        user.updateUser(newUser.getUserName(), newUser.getEmail(), newUser.getPassword(), newUser.getPhoneNumber(),
                newUser.getCredits(), newUser.getAccess(), newUser.getLevel(), newUser.getCurExpPoint(),
                newUser.getStamina(), newUser.getMoney(), newUser.getGrade(), newUser.getEngKnowledge(),
                newUser.getMathKnowledge(), newUser.getChiKnowledge(), newUser.getIdentity());

        UserRepository.updateUserStatus(user, newUser.getUserId());
        // return "Modified User";
        return user;

    }

    // 获取所有用户信息
    @Override
    public List<User> getAllUsers() {
        return UserRepository.findAll();
    }

    // 删除部分用户
    @Override
    public String deleteUsers(List<Integer> UserIds) {
        for (Integer userId : UserIds) {
            UserRepository.deleteById(userId);
        }
        return "Deleted Users by id";
    }

    // 删除所有用户
    @Override
    public String deleteAll() {
        UserRepository.deleteAll();
        return "Deleted All Users";
    }

    // 使用用户名查询用户
    @Override
    public User getOneUserByUserName(String userName) {
        return UserRepository.findUserByUserNameEquals(userName);
    }

    // 删除一个指定用户
    @Override
    public List<User> deleteUser(Integer userId) {
        UserRepository.deleteById(userId);
        return getAllUsers();
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        return this.ListPage(page_token, page_size, UserRepository, null);
    }
}
