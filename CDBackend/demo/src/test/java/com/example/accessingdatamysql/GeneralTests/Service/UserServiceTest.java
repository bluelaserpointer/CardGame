package com.example.accessingdatamysql.GeneralTests.Service;

import static org.junit.Assert.*;

import com.example.accessingdatamysql.UnitTestDemoApplicationTests;
import com.example.accessingdatamysql.dao.UserDao;
import com.example.accessingdatamysql.entity.User;
import com.example.accessingdatamysql.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
public class UserServiceTest extends UnitTestDemoApplicationTests {

    @Autowired
    private UserService testingService;
    @Autowired
    private UserDao testDao;

    @Test
    public void contextLoads() {

    }

    @Test
    public void identifyUserFalse() {
        String userName = "identifyUserFalse";
        String password = "identifyUserFalse";
        assertFalse(testingService.identifyUser(userName, password));
    }

    @Test
    public void identifyUserTrue() {
        String userName = "identifyUserTrue";
        String password = "identifyUserTrue";
        assertTrue(testingService.identifyUser(userName, password));
    }

    @Test
    @Transactional
    @Rollback
    public void addExpAndLvlUpCnd() {
        User user = testDao.getOneUserByUserName("addExpLvlUp");
        Integer currentUsrLvl = user.getLevel();
        testingService.addExp(user.getUserId(), 10);
        User nuser = testDao.getOneUserByUserName("addExpLvlUp");
        assertTrue(nuser.getLevel().equals(currentUsrLvl + 1));
    }

}