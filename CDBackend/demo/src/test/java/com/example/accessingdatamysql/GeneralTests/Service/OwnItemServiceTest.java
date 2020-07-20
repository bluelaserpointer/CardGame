package com.example.accessingdatamysql.GeneralTests.Service;

import static org.junit.Assert.*;

import com.example.accessingdatamysql.UnitTestDemoApplicationTests;
import com.example.accessingdatamysql.dao.OwnItemDao;
import com.example.accessingdatamysql.entity.OwnItem;
import com.example.accessingdatamysql.repository.OwnItemRepository;
import com.example.accessingdatamysql.service.OwnItemService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
public class OwnItemServiceTest extends UnitTestDemoApplicationTests {

    @Autowired
    private OwnItemService testingService;
    @Autowired
    private OwnItemDao testDao;
    @Autowired
    private OwnItemRepository testRepository;

    @Test
    public void contextLoads() {

    }

    @Test
    public void getAllOwnItemsByUserId() {
        Integer serviceCount = testingService.getAllOwnItemsByUserId(1).size();
        Integer daoCount = testDao.getAllOwnItemsByUserId(1).size();
        assertTrue(serviceCount.equals(daoCount));
    }

}