package com.example.accessingdatamysql.GeneralTests.Controller;

// import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
// import com.example.accessingdatamysql.controller.ChapterController;
import com.example.accessingdatamysql.entity.AuthRequest;

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
public class ChapterControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private String TOKEN;

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

    @Test
    @DisplayName("File: ChapterController Method: contextLoads")
    public void contextLoads() {
    }

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

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ChapterController Method: updateChapterPhaseStrategy")
    public void updateChapterPhaseStrategy() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc.perform(get(

                "/chapter/updateChapterPhaseStrategy?chapterId=1&phaseId=1&phaseData=updateChapterPhaseStrategyTest")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))

                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ChapterController Method: updateChapterPhaseAwards")
    public void updateChapterPhaseAwards() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc.perform(get(

                "/chapter/updateChapterPhaseAwards?chapterId=1&phaseId=1&awardItems=updateChapterPhaseAwardsTest&awardCards=updateChapterPhaseAwardsTest")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))

                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ChapterController Method: updateChapterAwards")
    public void updateChapterAwards() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc.perform(get(

                "/chapter/updateChapterAwards?chapterId=1&awardItems=updateChapterAwardsTest&awardCards=updateChapterAwardsTest")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))

                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ChapterController Method: getAllChapters")
    public void getAllChapters() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc

                .perform(get("/chapter/getAllChapters").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", token))

                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ChapterController Method: getChapterDetailsByChapterAndByPhase")
    public void getChapterDetailsByChapterAndByPhase() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc

                .perform(get("/chapter/getChapterDetailsByChapterAndByPhase?chapterId=1&phaseId=1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))

                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ChapterController Method: getChapterDetailsByChapter")
    public void getChapterDetailsByChapter() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc
                .perform(get("/Chapter/getChapterDetailsByChapter?chapterId=1&phaseId=1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))

                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ChapterController Method: getAllChapterDetails")
    public void getAllChapterDetails() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc
                .perform(get("/Chapter/getAllChapterDetails").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", token))

                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}