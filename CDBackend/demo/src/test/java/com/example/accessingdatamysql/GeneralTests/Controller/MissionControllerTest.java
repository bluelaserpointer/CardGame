package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.accessingdatamysql.controller.MissionController;

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
public class MissionControllerTest {

        @Test
        @DisplayName("File: MissionController Method: contextLoads")
        public void contextLoads() {
                assertThat(missionController).isNotNull();
        }

        private MockMvc mockMvc;

        @Autowired
        private MissionController missionController;

        @Before
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(missionController).build();
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
        @DisplayName("File: MissionController Method: findMissionByMissionId")
        public void findMissionByMissionId() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/mission/getMission?missionId=84")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MissionController Method: addNewMission")
        public void addNewMission() throws Exception {
                MvcResult result = mockMvc.perform(get(
                                "/mission/addMission?type=MissionAddTest&missionName=MissionAddTest&missionDescription=MissionAddTest&awardItemIds=1")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MissionController Method: updateMission")
        public void updateMission() throws Exception {
                MvcResult result = mockMvc.perform(get(
                                "/mission/updateMission?missionId=84&type=MissionAddTest&missionName=MissionAddTest&missionDescription=MissionAddTest&awardItemIds=1")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MissionController Method: getAllMissions")
        public void getAllMissions() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/mission/getAllMissions").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MissionController Method: deleteMissions")
        public void deleteMissions() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/mission/deleteMissions?missionIds=84")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MissionController Method: deleteAllMissions")
        public void deleteAllMissions() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/mission/deleteAllMissions")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MissionController Method: deleteMission")
        public void deleteMission() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/mission/deleteMission?missionId=84")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}