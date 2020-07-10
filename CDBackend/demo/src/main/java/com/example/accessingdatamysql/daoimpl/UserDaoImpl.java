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

    @Override
    public User getOneUser(Integer UserId) {
        return UserRepository.getOne(UserId);
    }

    public String addNewUser(String userName, String email, String password, String phoneNumber) {
        User User = new User(userName, email, password, phoneNumber);
        // System.out.println("new User has an Id of : " + n.getUserId());
        UserRepository.save(User);
        return "Saved User";
    }

    public String updateUser(Integer UserId, String userName, String email, String password, String phoneNumber,
            Integer credits, Boolean access, Integer level, Integer stamina, Integer money, Integer engKnowledge,
            Integer mathKnowledge, Integer chiKnowledge) {

        User User = UserRepository.getOne(UserId);
        // System.out.println("old User has an Id of : " + n.getUserId());
        User.updateUser(userName, email, password, phoneNumber, credits, access, level, stamina, money, engKnowledge,
                mathKnowledge, chiKnowledge);

        UserRepository.updateUserStatus(User, UserId);
        // return "Modified User";
        return "modified User: " + User.getUserName();

    }

    public List<User> getAllUsers() {
        List<User> Users = UserRepository.findAll();
        return Users;
    }

    public String deleteUsers(List<Integer> UserIds) {
        for (int i = 0; i < UserIds.size(); i++) {
            UserRepository.deleteById(UserIds.get(i));
        }
        return "Deleted Users by id";
    }

    public String deleteAll() {
        UserRepository.deleteAll();
        return "Deleted All Users";
    }

    public User getOneUserByUserName(String userName) {
        return UserRepository.findUserByUserNameEquals(userName);
    }

    public List<User> deleteUser(Integer userId) {
        UserRepository.deleteById(userId);
        return getAllUsers();
    }
}
