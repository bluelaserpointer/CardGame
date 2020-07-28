package com.example.accessingdatamysql;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.fastjson.JSON;
import com.example.accessingdatamysql.controller.ActivityController;
import com.example.accessingdatamysql.controller.UserController;
import com.example.accessingdatamysql.entity.Activity;
import com.example.accessingdatamysql.entity.ActivityDetails;
import com.example.accessingdatamysql.entity.AuthRequest;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// @WebAppConfiguration
public class UnitTestDemoApplicationTests {

    // @Before
    // public void init() {
    // System.out.println("Start Unit Testing:");
    // }
    @Autowired
    AccessingDataMysqlApplication accessingDataMysqlApplication;
    // @Autowired
    // private UserController userController;

    // private MockMvc mockMvc;

    // private String TOKEN;

    // @PostConstruct
    // public void setUp() throws Exception {
    // mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

    // AuthRequest user = new AuthRequest();
    // user.setPassword("postTest");
    // user.setUserName("postTest");

    // String body = JSON.toJSONString(user);
    // System.out.println(body);
    // MvcResult result = mockMvc
    // .perform(MockMvcRequestBuilders.post("http://localhost:8080/user/login")
    // .contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
    // .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    // TOKEN = result.getResponse().getContentAsString();
    // TOKEN = "Bearer " + TOKEN;
    // System.out.println(TOKEN);
    // }

    // @Test
    // public void testA() {
    // }
    // @After
    // public void after() {
    // System.out.println("End Unit Testing.");
    // }

    @Test
    public void contextLoads() {
        assertThat(accessingDataMysqlApplication).isNotNull();
    }

}