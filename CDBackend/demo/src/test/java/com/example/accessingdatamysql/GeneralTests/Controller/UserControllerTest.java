package com.example.accessingdatamysql.GeneralTests.Controller;

import com.example.accessingdatamysql.UnitTestDemoApplicationTests;
import com.example.accessingdatamysql.controller.UserController;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

// @WebMvcTest(UserController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends UnitTestDemoApplicationTests {

        @Test
        public void contextLoads() {
                assertThat(userController).isNotNull();
        }

        private MockMvc mockMvc;

        @Autowired
        private UserController userController;

        @Before
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        }

        @AfterEach
        void tearDown() {

        }

        @AfterAll
        static void tearDownAll() {
        }

        // 测试获取单个userId（需要在数据库中存在userId为1的用户）
        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: findUserByUserId")
        public void findUserByUserId() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/user/getUser?userId=1").contentType(MediaType.APPLICATION_JSON_VALUE))
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
                // User testUser = new User("add2","add2","add2","add2");
                MvcResult result = mockMvc
                                .perform(get("/user/addUser?userName=add2&email=add2&password=add2&phoneNumber=add2")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        // 测试更新一个用户
        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: updateUser")
        public void updateUser() throws Exception {
                MvcResult result = mockMvc.perform(get(
                                "/user/updateUser?userId=1&userName=add3&email=add3&password=add3&phoneNumber=add3&credits=1000&access=1&level=10&curExpPoint=10&stamina=5&money=99&grade=10&engKnowledge=5&mathKnowledge=100&chiKnowledge=2")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk()).andExpect(jsonPath("$.level").value("10"))
                                .andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        // 测试获取所有用户
        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: getAllUsers")
        public void getAllUsers() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/user/getAllUsers").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: identifyUser")
        public void identifyUser() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/user/identifyUser?userName=identifyUserTrue&password=identifyUserTrue")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: addExp")
        public void addExp() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/user/addExp?userId=1&exp=100")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: deleteUsers")
        public void deleteUsers() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/user/deleteUsers?userIds=1")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: deleteUser")
        public void deleteUser() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/user/deleteUser?userId=2").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: UserController Method: deleteAll")
        public void deleteAll() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/user/deleteAllUsers").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}