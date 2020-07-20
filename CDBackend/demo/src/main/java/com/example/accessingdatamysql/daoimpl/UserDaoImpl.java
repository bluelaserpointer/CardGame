package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.UserDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.io.Console;
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
    public User addNewUser(String userName, String email, String password, String phoneNumber) {
        User User = new User(userName, email, password, phoneNumber);
        // System.out.println("new User has an Id of : " + n.getUserId());
        UserRepository.save(User);
        return User;
    }

    // 更新一个用户信息
    @Override
    public User updateUser(Integer UserId, String userName, String email, String password, String phoneNumber,
            Integer credits, Boolean access, Integer level, Integer curExpPoint, Integer stamina, Integer money,
            Double grade, Integer engKnowledge, Integer mathKnowledge, Integer chiKnowledge) {

        User User = UserRepository.getOne(UserId);
        // System.out.println("old User has an Id of : " + n.getUserId());
        User.updateUser(userName, email, password, phoneNumber, credits, access, level, curExpPoint, stamina, money,
                grade, engKnowledge, mathKnowledge, chiKnowledge);

        UserRepository.updateUserStatus(User, UserId);
        // return "Modified User";
        return User;

    }

    // 获取所有用户信息
    @Override
    public List<User> getAllUsers() {
        List<User> Users = UserRepository.findAll();
        return Users;
    }

    // 删除部分用户
    @Override
    public String deleteUsers(List<Integer> UserIds) {
        for (int i = 0; i < UserIds.size(); i++) {
            UserRepository.deleteById(UserIds.get(i));
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
}
