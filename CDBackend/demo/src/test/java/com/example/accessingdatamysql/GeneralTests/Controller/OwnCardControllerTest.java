package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.accessingdatamysql.controller.OwnCardController;

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
public class OwnCardControllerTest {

        @Test
        @DisplayName("File: OwnCardController Method: contextLoads")
        public void contextLoads() {
                assertThat(ownCardController).isNotNull();
        }

        private MockMvc mockMvc;

        @Autowired
        private OwnCardController ownCardController;

        @Before
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(ownCardController).build();
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
        @DisplayName("File: OwnCardController Method: findOwnCardByOwnCardId")
        public void findOwnCardByOwnCardId() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownCard/getOwnCard?ownCardId=88")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: addNewOwnCard")
        public void addNewOwnCard() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownCard/addOwnCard?userId=12&cardId=10")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: updateOwnCard")
        public void updateOwnCard() throws Exception {
                MvcResult result = mockMvc.perform(get(
                                "/ownCard/updateOwnCard?userId=12&cardId=10&cardLevel=10&cardCurExp=10&cardLevelLimit=10&repetitiveOwns=1&accquireDate=2018-01-19 03:14:07.999999")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: cardLevelUp")
        public void cardLevelUp() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownCard/cardLevelUp?userId=12&cardId=10")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: ownAnotherCard")
        public void ownAnotherCard() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownCard/ownAnotherCard?userId=12&cardId=10")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: getAllOwnCards")
        public void getAllOwnCards() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownCard/getAllOwnCards").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: getAllOwnCardsByUserId")
        public void getAllOwnCardsByUserId() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownCard/getAllOwnCardsByUserId?userId=12")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: deleteOwnCards")
        public void deleteOwnCards() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownCard/deleteOwnCards?ownCardIds=88")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: deleteAllOwnCards")
        public void deleteAllOwnCards() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownCard/deleteAllOwnCards")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: deleteOwnCard")
        public void deleteOwnCard() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownCard/deleteOwnCard?userId=12&cardId=10")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}