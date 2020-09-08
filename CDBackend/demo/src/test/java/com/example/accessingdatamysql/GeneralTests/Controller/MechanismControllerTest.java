package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.controller.MechanismController;
import com.example.accessingdatamysql.entity.AuthRequest;
import com.example.accessingdatamysql.entity.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MechanismControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private String TOKEN;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void tearDownAll() {

    }

    @Transactional
    @Rollback(value = true)
    public String getTOKEN() throws Exception {
        // System.out.println(TOKEN);
        User addedUser = new User("postTest", "postTest", "postTest", "postTest", "ROLE_ADMIN");
        String addU = JSON.toJSONString(addedUser);
        // System.out.println(addU);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/user/unitTest")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(addU))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        AuthRequest user = new AuthRequest();
        user.setPassword("postTest");
        user.setUserName("postTest");

        String body = JSON.toJSONString(user);
        System.out.println(body);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("http://localhost:8080/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        TOKEN = result.getResponse().getContentAsString();
        System.out.println(TOKEN);
        JSONObject temp = JSON.parseObject(TOKEN);
        TOKEN = temp.getString("token");
        TOKEN = "Bearer " + TOKEN;
        System.out.println(TOKEN);
        return TOKEN;
    }

    public User addUserBeforeTest(String token) throws Exception {
        User User = new User("addUserBeforeTest", "addUserBeforeTest", "addUserBeforeTest", "addUserBeforeTest",
                "addUserBeforeTest");

        String body = JSON.toJSONString(User);

        // System.out.println(body);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/user/register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());

        // System.out.println(result.getResponse().getContentAsString());
        String json = result.getResponse().getContentAsString();
        JSONObject temp = JSON.parseObject(json);
        json = temp.getString("user");
        System.out.println(json);
        User = JSON.parseObject(json, User.class);
        return User;
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: MechanismController Method: drawCard")
    public void drawCard() throws Exception {
        String token = getTOKEN();
        User add = addUserBeforeTest(token);
        MvcResult result = mockMvc
                .perform(get("/mechanism/drawCard?userId=" + add.getUserId() + "&chi=10&mat=10&eng=10")
                        .header("Authorization", token).contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}