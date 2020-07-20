package com.example.accessingdatamysql.GeneralTests.Service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.example.accessingdatamysql.UnitTestDemoApplicationTests;
import com.example.accessingdatamysql.entity.User;
import com.example.accessingdatamysql.service.UserService;

import org.junit.Test;

public class UserServiceTest extends UnitTestDemoApplicationTests {

    // @Mock
    UserService testing = mock(UserService.class);

    @Test
    public void contextLoads() {

    }

    @Test
    public void getOneUser() {
        User user = new User();
        when(testing.getOneUser(1)).thenReturn(user);
        assertEquals(testing.getOneUser(1), user);
    }

}