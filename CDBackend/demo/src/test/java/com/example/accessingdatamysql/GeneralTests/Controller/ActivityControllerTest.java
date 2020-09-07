package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.controller.ActivityController;
import com.example.accessingdatamysql.controller.UserController;
import com.example.accessingdatamysql.entity.Activity;
import com.example.accessingdatamysql.entity.ActivityDetails;
import com.example.accessingdatamysql.entity.AuthRequest;
import com.example.accessingdatamysql.entity.ListRequest;
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

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ActivityControllerTest {

        @Autowired
        private WebApplicationContext context;

        private MockMvc mockMvc;

        private String TOKEN;

        // @Autowired
        // private ActivityController activityController;

        // @Autowired
        // private UserController userController;

        // private MockMvc mvc;

        // @Before
        // public void setup() {
        // mvc = MockMvcBuilders
        // .webAppContextSetup(context)
        // .apply(SecurityMockMvcConfigurers.springSecurity())
        // .build();
        // }

        // @Test
        // @DisplayName("File: ActivityController Method: contextLoads")
        // public void contextLoads() {
        // assertThat(activityController).isNotNull();
        // }

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

        @Transactional
        @Rollback(value = true)
        public Activity addActivityBeforeTest(String token) throws Exception {
                // String token = getTOKEN();
                System.out.println(token);
                Timestamp start = new Timestamp(System.currentTimeMillis());
                Activity activity = new Activity("addNewActivity", "addNewActivity", start);
                ActivityDetails activityDetails = new ActivityDetails();
                activityDetails.setActivityDescription("addNewActivity");
                activityDetails.setActivityImg("addNewActivity");
                activity.setActivityDetails(activityDetails);

                String body = JSON.toJSONString(activity);

                // System.out.println(body);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("/activity/addActivity")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());

                // System.out.println(result.getResponse().getContentAsString());
                String json = result.getResponse().getContentAsString();
                activity = JSON.parseObject(json, Activity.class);
                return activity;
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ActivityController Method: findActivityByActivityId")
        public void findActivityByActivityId() throws Exception {
                String token = getTOKEN();
                Activity addedActivity = addActivityBeforeTest(token);
                MvcResult result = mockMvc
                                .perform(get("/activity/getActivity?activityId=" + addedActivity.getActivityId())
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ActivityController Method: addNewActivity")
        public void addNewActivity() throws Exception {
                String token = getTOKEN();
                System.out.println(token);
                Timestamp start = new Timestamp(System.currentTimeMillis());
                Activity activity = new Activity("addNewActivity", "addNewActivity", start);
                ActivityDetails activityDetails = new ActivityDetails();
                activityDetails.setActivityDescription("addNewActivity");
                activityDetails.setActivityImg("addNewActivity");
                activity.setActivityDetails(activityDetails);

                String body = JSON.toJSONString(activity);

                // System.out.println(body);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("/activity/addActivity")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                // activity.setActivityId();

        }

        // @Test
        // @Transactional
        // @Rollback(value = true)
        // @DisplayName("File: ActivityController Method: updateActivity")
        // public void updateActivity() throws Exception {
        //         String token = getTOKEN();
        //         Activity addedActivity = addActivityBeforeTest(token);
        //         Timestamp start = new Timestamp(System.currentTimeMillis());
        //         Activity activity = new Activity("addNewActivity", "addNewActivity", start);
        //         ActivityDetails activityDetails = new ActivityDetails();
        //         activityDetails.setActivityDescription("addNewActivity");
        //         activityDetails.setActivityImg("addNewActivity");
        //         activity.setActivityDetails(activityDetails);
        //         activity.setActivityId(addedActivity.getActivityId());

        //         String body = JSON.toJSONString(activity);
        //         MvcResult result = mockMvc
        //                         .perform(MockMvcRequestBuilders.post("/activity/updateActivity")
        //                                         .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
        //                                         .header("Authorization", token))
        //                         .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
        //                         .andReturn();
        //         System.out.println(result.getResponse().getContentAsString());
        // }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ActivityController Method: getAllActivities")
        public void getAllActivities() throws Exception {
                String token = getTOKEN();
                MvcResult result = mockMvc
                                .perform(get("/activity/getAllActivities").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ActivityController Method: deleteActivities")
        public void deleteActivities() throws Exception {
                String token = getTOKEN();
                Activity addedActivity = addActivityBeforeTest(token);
                MvcResult result = mockMvc
                                .perform(get("/activity/deleteActivities?activityIds=" + addedActivity.getActivityId())
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ActivityController Method: deleteAllActivities")
        public void deleteAllActivities() throws Exception {
                MvcResult result = mockMvc.perform(get("/activity/deleteAllActivities")
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", getTOKEN()))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ActivityController Method: deleteActivity")
        public void deleteActivity() throws Exception {
                String token = getTOKEN();
                Activity addedActivity = addActivityBeforeTest(token);
                MvcResult result = mockMvc
                                .perform(get("/activity/deleteActivity?activityId=" + addedActivity.getActivityId())
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ActivityController Method: ListPage")
        public void ListPage() throws Exception {
                String token = getTOKEN();
                // Activity addedActivity = addActivityBeforeTest(token);

                ListRequest listRequest = new ListRequest();
                listRequest.setPageSize(10);
                listRequest.setPageToken(1);

                String body = JSON.toJSONString(listRequest);
                System.out.println(body);
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/activity/List").content(body)
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}