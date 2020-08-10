package com.example.accessingdatamysql.GeneralTests.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.UnitTestDemoApplicationTests;
import com.example.accessingdatamysql.controller.UserController;
import com.example.accessingdatamysql.entity.AuthRequest;
import com.example.accessingdatamysql.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

// @WebMvcTest(UserController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends UnitTestDemoApplicationTests {

        // @Test
        // public void contextLoads() {
        // assertThat(userController).isNotNull();
        // }

        // private MockMvc mockMvc;

        // @Autowired
        // private UserController userController;

        // @Before
        // public void setUp() {
        // mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        // }

        @Autowired
        private WebApplicationContext context;

        private MockMvc mockMvc;

        private String TOKEN;

        @Before
        public void setUp() throws Exception {
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
                                .perform(MockMvcRequestBuilders.post("/user/register")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());

                // System.out.println(result.getResponse().getContentAsString());
                String json = result.getResponse().getContentAsString();
                JSONObject temp = JSON.parseObject(json);
                json = temp.getString("user");
                System.out.println(json);
                User = JSON.parseObject(json, User.class);
                return User;
        }

        // 测试获取单个userId（需要在数据库中存在userId为1的用户）
        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: findUserByUserId")
        public void findUserByUserId() throws Exception {
                String token = getTOKEN();
                User addedUser = addUserBeforeTest(token);
                MvcResult result = mockMvc.perform(get("/user/getUser?userId=" + addedUser.getUserId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        // 测试添加一个新用户
        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: addNewUser")
        public void addNewUser() throws Exception {
                String token = getTOKEN();
                User User = new User("addUserBeforeTest", "addUserBeforeTest", "addUserBeforeTest", "addUserBeforeTest",
                                "addUserBeforeTest");

                String body = JSON.toJSONString(User);

                // System.out.println(body);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("/user/register")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        // 测试更新一个用户
        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: updateUser")
        public void updateUser() throws Exception {
                String token = getTOKEN();
                User addedUser = addUserBeforeTest(token);

                addedUser.setUserName("updateUser");

                String body = JSON.toJSONString(addedUser);

                MvcResult result = mockMvc
                                .perform(get("/user/updateUser").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(body).header("Authorization", token))
                                .andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        // 测试获取所有用户
        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: getAllUsers")
        public void getAllUsers() throws Exception {
                String token = getTOKEN();
                MvcResult result = mockMvc
                                .perform(get("/user/getAllUsers").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: confirmDelete")
        public void confirmDelete() throws Exception {
                String token = getTOKEN();
                User addedUser = addUserBeforeTest(token);
                MvcResult result = mockMvc
                                .perform(get("/user/confirmDelete?userName=" + addedUser.getUserName() + "&password="
                                                + addedUser.getPassword()).contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                .header("Authorization", token))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: login")
        public void login() throws Exception {
                String token = getTOKEN();
                AuthRequest user = new AuthRequest();
                user.setPassword("postTest");
                user.setUserName("postTest");

                String body = JSON.toJSONString(user);
                System.out.println(body);
                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("http://localhost:8080/user/login")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: addExp")
        public void addExp() throws Exception {
                String token = getTOKEN();
                User addedUser = addUserBeforeTest(token);
                MvcResult result = mockMvc.perform(get("/user/addExp?userId=" + addedUser.getUserId() + "&exp=1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: deleteUsers")
        public void deleteUsers() throws Exception {
                String token = getTOKEN();
                User addedUser = addUserBeforeTest(token);
                MvcResult result = mockMvc.perform(get("/user/deleteUsers?userIds=" + addedUser.getUserId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: deleteUser")
        public void deleteUser() throws Exception {
                String token = getTOKEN();
                User addedUser = addUserBeforeTest(token);
                MvcResult result = mockMvc.perform(get("/user/deleteUser?userId=" + addedUser.getUserId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: deleteAll")
        public void deleteAll() throws Exception {
                String token = getTOKEN();
                MvcResult result = mockMvc
                                .perform(get("/user/deleteAllUsers").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}