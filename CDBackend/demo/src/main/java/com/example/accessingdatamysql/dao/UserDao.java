package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface UserDao {
        User getOneUser(Integer UserId);

        String addNewUser(String userName, String email, String password, String phoneNumber);

        String updateUser(Integer UserId, String userName, String email, String password, String phoneNumber,
                        Integer credits, Boolean access, Integer level, Integer stamina, Integer money,
                        Integer engKnowledge, Integer mathKnowledge, Integer chiKnowledge);

        List<User> getAllUsers();

        String deleteUsers(List<Integer> UserIds);

        String deleteAll();

        User getOneUserByUserName(String userName);

        List<User> deleteUser(Integer userId);
}
