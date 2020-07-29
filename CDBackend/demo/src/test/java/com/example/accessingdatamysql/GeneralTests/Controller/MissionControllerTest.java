package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
import com.example.accessingdatamysql.controller.MissionController;
import com.example.accessingdatamysql.entity.AuthRequest;
import com.example.accessingdatamysql.entity.Mission;
import com.example.accessingdatamysql.entity.MissionDetails;

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
public class MissionControllerTest {

    // @Test
    // @DisplayName("File: MissionController Method: contextLoads")
    // public void contextLoads() {
    // assertThat(missionController).isNotNull();
    // }

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private String TOKEN;
    // @Autowired
    // private MissionController missionController;

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

    public String getTOKEN() throws Exception {
        System.out.println(TOKEN);
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
        TOKEN = "Bearer " + TOKEN;
        System.out.println(TOKEN);
        return TOKEN;
    }

    public Mission addMissionBeforeTest() throws Exception {
        String token = getTOKEN();
        Mission Mission = new Mission("addMissionBeforeTest", "addMissionBeforeTest");
        MissionDetails MissionDetails = new MissionDetails();
        MissionDetails.setMissionDescription("addMissionBeforeTest");
        Mission.setMissionDetails(MissionDetails);

        String body = JSON.toJSONString(Mission);

        // System.out.println(body);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/mission/addMission")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(body).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());

        // System.out.println(result.getResponse().getContentAsString());
        String json = result.getResponse().getContentAsString();
        Mission = JSON.parseObject(json, Mission.class);
        return Mission;
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: MissionController Method: findMissionByMissionId")
    public void findMissionByMissionId() throws Exception {
        String token = getTOKEN();
        Mission addedMission = addMissionBeforeTest();
        MvcResult result = mockMvc
                .perform(get("/mission/getMission?missionId=" + addedMission.getMissionId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: MissionController Method: addNewMission")
    public void addNewMission() throws Exception {
        String token = getTOKEN();
        Mission Mission = new Mission("addNewMission", "addNewMission");
        MissionDetails MissionDetails = new MissionDetails();
        MissionDetails.setMissionDescription("addNewMission");
        Mission.setMissionDetails(MissionDetails);

        String body = JSON.toJSONString(Mission);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/mission/addMission")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(body).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: MissionController Method: updateMission")
    public void updateMission() throws Exception {
        String token = getTOKEN();
        Mission addedMission = addMissionBeforeTest();
        Mission Mission = new Mission("updateMission", "updateMission");
        MissionDetails MissionDetails = new MissionDetails(addedMission.getMissionId(), "updateMission");
        Mission.setMissionDetails(MissionDetails);
        Mission.setMissionId(addedMission.getMissionId());

        String body = JSON.toJSONString(Mission);
        MvcResult result = mockMvc
                .perform(get("/mission/updateMission?missionId=" + addedMission.getMissionId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(body).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: MissionController Method: getAllMissions")
    public void getAllMissions() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc
                .perform(get("/mission/getAllMissions").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: MissionController Method: deleteMissions")
    public void deleteMissions() throws Exception {
        String token = getTOKEN();
        Mission addedMission = addMissionBeforeTest();
        MvcResult result = mockMvc
                .perform(get("/mission/deleteMissions?missionIds=" + addedMission.getMissionId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: MissionController Method: deleteAllMissions")
    public void deleteAllMissions() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc
                .perform(get("/mission/deleteAllMissions").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: MissionController Method: deleteMission")
    public void deleteMission() throws Exception {
        String token = getTOKEN();
        Mission addedMission = addMissionBeforeTest();
        MvcResult result = mockMvc
                .perform(get("/mission/deleteMission?missionId=" + addedMission.getMissionId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}