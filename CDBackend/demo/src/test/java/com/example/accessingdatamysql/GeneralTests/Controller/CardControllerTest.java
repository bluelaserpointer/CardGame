package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
import com.example.accessingdatamysql.controller.CardController;
import com.example.accessingdatamysql.entity.AuthRequest;
import com.example.accessingdatamysql.entity.Card;
import com.example.accessingdatamysql.entity.CardDetails;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {

        @Autowired
        private WebApplicationContext context;

        private MockMvc mockMvc;

        private String TOKEN;

        @Test
        @DisplayName("File: cardController Method: contextLoads")
        public void contextLoads() {
                // assertThat(cardController).isNotNull();
        }

        @Before
        public void setUp() throws Exception {
                mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
                                .build();
                getTOKEN();
        }

        @AfterEach
        void tearDown() {

        }

        @AfterAll
        static void tearDownAll() {

        }

        public Card addCardBeforeTest() throws Exception {
                String token = getTOKEN();
                Card card = new Card("addCardBeforeTest", "addCardBeforeTest", 1, 1, 1, 1, 1.0, 1, 1);
                CardDetails cardDetails = new CardDetails();
                cardDetails.setCardDescription("addCardBeforeTest");
                cardDetails.setCardImg("addCardBeforeTest");
                cardDetails.setShortDescription("addCardBeforeTest");
                card.setCardDetails(cardDetails);
                String body = JSON.toJSONString(card);
                MvcResult result = mockMvc
                                .perform(get("/card/addCard").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(body).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());

                String json = result.getResponse().getContentAsString();
                card = JSON.parseObject(json, Card.class);
                return card;
        }

        public String getTOKEN() throws Exception {
                User addedUser = new User("postTest", "postTest", "postTest", "postTest", "ROLE_ADMIN");
                String addU = JSON.toJSONString(addedUser);
                mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/user/register")
                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(addU))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
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
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: findCardByCardId")
        public void findCardByCardId() throws Exception {
                String token = getTOKEN();
                Card addedCard = addCardBeforeTest();
                MvcResult result = mockMvc.perform(get("/card/getCard?cardId=" + addedCard.getCardId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: addNewCard")
        public void addNewCard() throws Exception {
                String token = getTOKEN();
                Card card = new Card("cardAddTest", "cardAddTest", 1, 1, 1, 1, 1.0, 1, 1);
                CardDetails cardDetails = new CardDetails();
                cardDetails.setCardDescription("addCardBeforeTest");
                cardDetails.setCardImg("addCardBeforeTest");
                cardDetails.setShortDescription("addCardBeforeTest");
                card.setCardDetails(cardDetails);
                String body = JSON.toJSONString(card);
                MvcResult result = mockMvc
                                .perform(get("/card/addCard").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(body).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: updateCard")
        public void updateCard() throws Exception {
                Card addedCard = addCardBeforeTest();
                String token = getTOKEN();
                Card card = new Card("updateCard", "updateCard", 1, 1, 1, 1, 1.0, 1, 1);
                CardDetails cardDetails = new CardDetails();
                cardDetails.setCardDescription("updateCard");
                cardDetails.setCardImg("updateCard");
                cardDetails.setShortDescription("updateCard");
                card.setCardDetails(cardDetails);
                card.setCardId(addedCard.getCardId());

                String body = JSON.toJSONString(card);
                MvcResult result = mockMvc
                                .perform(get("/card/updateCard").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(body).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: getAllCards")
        public void getAllCards() throws Exception {
                String token = getTOKEN();
                MvcResult result = mockMvc
                                .perform(get("/card/getAllCards").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: deleteCards")
        public void deleteCards() throws Exception {
                String token = getTOKEN();
                Card addedCard = addCardBeforeTest();
                MvcResult result = mockMvc.perform(get("/card/deleteCards?cardIds=" + addedCard.getCardId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: deleteAllCards")
        public void deleteAllCards() throws Exception {
                String token = getTOKEN();
                MvcResult result = mockMvc
                                .perform(get("/card/deleteAllCards").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: cardController Method: deleteCard")
        public void deleteCard() throws Exception {
                String token = getTOKEN();
                Card addedCard = addCardBeforeTest();
                MvcResult result = mockMvc.perform(get("/card/deleteCard?cardId=" + addedCard.getCardId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}