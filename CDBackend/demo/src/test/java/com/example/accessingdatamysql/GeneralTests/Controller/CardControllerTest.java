package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.accessingdatamysql.controller.CardController;

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
public class CardControllerTest {

        @Test
        @DisplayName("File: cardController Method: contextLoads")
        public void contextLoads() {
                assertThat(cardController).isNotNull();
        }

        private MockMvc mockMvc;

        @Autowired
        private CardController cardController;

        @Before
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
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
        @DisplayName("File: cardController Method: findCardByCardId")
        public void findCardByCardId() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/card/getCard?cardId=71").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: addNewCard")
        public void addNewCard() throws Exception {
                MvcResult result = mockMvc.perform(get(
                                "/card/addCard?cardName=cardAddTest&rarity=cardAddTest&healthPoint=1&attack=1&defense=1&attackRange=1&cd=1.0&speed=1&cardImg=cardAddTest&shortDescription=cardAddTest&cardDescription=cardAddTest")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: updateCard")
        public void updateCard() throws Exception {
                MvcResult result = mockMvc.perform(get(
                                "/card/updateCard?cardId=71&cardName=cardAddTest&rarity=cardAddTest&healthPoint=100&attack=1&defense=1&attackRange=1&cd=1.0&speed=1&cardImg=cardAddTest&shortDescription=cardAddTest&cardDescription=cardAddTest")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: getAllCards")
        public void getAllCards() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/card/getAllCards").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: deleteCards")
        public void deleteCards() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/card/deleteCards?cardIds=71")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: deleteAllCards")
        public void deleteAllCards() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/card/deleteAllCards").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: deleteCard")
        public void deleteCard() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/card/deleteCard?cardId=71")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}