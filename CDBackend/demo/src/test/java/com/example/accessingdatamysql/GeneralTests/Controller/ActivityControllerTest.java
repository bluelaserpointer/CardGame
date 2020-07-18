package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.accessingdatamysql.controller.ActivityController;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ActivityControllerTest {

    @Test
    @DisplayName("File: ActivityController Method: contextLoads")
    public void contextLoads() {
        assertThat(activityController).isNotNull();
    }

    private MockMvc mockMvc;

    @Autowired
    private ActivityController activityController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(activityController).build();
    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void tearDownAll() {

    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ActivityController Method: findActivityByActivityId")
    public void findActivityByActivityId() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/activity/getActivity?activityId=62").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ActivityController Method: addNewActivity")
    public void addNewActivity() throws Exception {
        MvcResult result = mockMvc.perform(get(
                "/activity/addActivity?activityName=addTest&activityImg=addTest&activityDescription=addTest&start=2018-01-19 03:14:07.999999&type=updateTest")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ActivityController Method: updateActivity")
    public void updateActivity() throws Exception {
        MvcResult result = mockMvc.perform(get(
                "/activity/updateActivity?activityId=61&activityName=updateTest&activityImg=updateTest&activityDescription=updateTest&start=2018-01-19 03:14:07.999999&type=updateTest")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ActivityController Method: getAllActivities")
    public void getAllActivities() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/activity/getAllActivities").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ActivityController Method: deleteActivities")
    public void deleteActivities() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/activity/deleteActivities?activityIds=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ActivityController Method: deleteAllActivities")
    public void deleteAllActivities() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/activity/deleteAllActivities").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ActivityController Method: deleteActivity")
    public void deleteActivity() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/activity/deleteActivity?activityId=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}