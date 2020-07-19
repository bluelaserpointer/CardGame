package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.accessingdatamysql.controller.ChapterController;

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
public class ChapterControllerTest {

        @Test
        @DisplayName("File: ChapterController Method: contextLoads")
        public void contextLoads() {
                assertThat(ChapterController).isNotNull();
        }

        private MockMvc mockMvc;

        @Autowired
        private ChapterController ChapterController;

        @Before
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(ChapterController).build();
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
        @DisplayName("File: ChapterController Method: updateChapterPhaseStrategy")
        public void updateChapterPhaseStrategy() throws Exception {
                MvcResult result = mockMvc.perform(get(
                                "/chapter/updateChapterPhaseStrategy?chapterId=1&phaseId=1&phaseData=updateChapterPhaseStrategyTest")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ChapterController Method: updateChapterPhaseAwards")
        public void updateChapterPhaseAwards() throws Exception {
                MvcResult result = mockMvc.perform(get(
                                "/chapter/updateChapterPhaseAwards?chapterId=1&phaseId=1&awardItems=updateChapterPhaseAwardsTest&awardCards=updateChapterPhaseAwardsTest")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ChapterController Method: updateChapterAwards")
        public void updateChapterAwards() throws Exception {
                MvcResult result = mockMvc.perform(get(
                                "/chapter/updateChapterAwards?chapterId=1&awardItems=updateChapterAwardsTest&awardCards=updateChapterAwardsTest")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ChapterController Method: getAllChapters")
        public void getAllChapters() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/chapter/getAllChapters").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ChapterController Method: getChapterDetailsByChapterAndByPhase")
        public void getChapterDetailsByChapterAndByPhase() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/chapter/getChapterDetailsByChapterAndByPhase?chapterId=1&phaseId=1")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ChapterController Method: getChapterDetailsByChapter")
        public void getChapterDetailsByChapter() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/Chapter/getChapterDetailsByChapter?chapterId=1&phaseId=1")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: ChapterController Method: getAllChapterDetails")
        public void getAllChapterDetails() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/Chapter/getAllChapterDetails")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}