package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Card;
import com.example.accessingdatamysql.entity.User;

import java.util.List;

public interface UserService {
    User getOneUser(Integer UserId);

    String addNewUser(String userName, String email, String password, String phoneNumber);

    String updateUser(Integer UserId, String userName, String email, String password, String phoneNumber,
            Integer credits, Boolean access, Integer level, Integer stamina, Integer money, Integer engKnowledge,
            Integer mathKnowledge, Integer chiKnowledge);

    List<User> getAllUsers();

    String deleteUsers(List<Integer> UserIds);

    String deleteAll();

    boolean identifyUser(String userName, String password);

    List<User> deleteUser(Integer userId);
}
