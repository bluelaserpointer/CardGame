package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.controller.OwnCardController;
import com.example.accessingdatamysql.entity.AuthRequest;
import com.example.accessingdatamysql.entity.Card;
import com.example.accessingdatamysql.entity.CardDetails;
import com.example.accessingdatamysql.entity.ListRequest;
import com.example.accessingdatamysql.entity.OwnCard;
import com.example.accessingdatamysql.entity.User;
import com.example.accessingdatamysql.service.UserService;
import com.example.accessingdatamysql.entity.OwnCard;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OwnCardControllerTest {

        // @Test
        // @DisplayName("File: OwnCardController Method: contextLoads")
        // public void contextLoads() {
        // assertThat(ownCardController).isNotNull();
        // }

        // private MockMvc mockMvc;

        // @Autowired
        // private OwnCardController ownCardController;

        // @Before
        // public void setUp() {
        // mockMvc = MockMvcBuilders.standaloneSetup(ownCardController).build();
        // }

        @Autowired
        private WebApplicationContext context;

        @Autowired

        private UserService userService;

        private MockMvc mockMvc;

        private String TOKEN;

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
        public OwnCard addOwnCardBeforeTest(String token) throws Exception {
                User addedUser = addUserBeforeTest(token);
                Card addedCard = addCardBeforeTest(token);
                // System.out.println(body);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders
                                                .post("/ownCard/addOwnCard?userId=" + addedUser.getUserId()
                                                                + "&cardId=" + addedCard.getCardId())
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());

                // System.out.println(result.getResponse().getContentAsString());
                String json = result.getResponse().getContentAsString();
                OwnCard OwnCard = JSON.parseObject(json, OwnCard.class);
                return OwnCard;
        }

        // @Transactional
        // @Rollback(value = true)
        public Card addCardBeforeTest(String token) throws Exception {
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

        // @Transactional
        // @Rollback(value = true)
        public User addUserBeforeTest(String token) throws Exception {
                User User = new User("addUserBeforeTest", "addUserBeforeTest", "addUserBeforeTest", "addUserBeforeTest",
                                "addUserBeforeTest");

                String body = JSON.toJSONString(User);

                // System.out.println(body);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("/user/register")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());

                // System.out.println(result.getResponse().getContentAsString());
                String json = result.getResponse().getContentAsString();
                JSONObject temp = JSON.parseObject(json);
                json = temp.getString("user");
                System.out.println(json);
                User = JSON.parseObject(json, User.class);
                return User;
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: findOwnCardByOwnCardId")
        public void findOwnCardByOwnCardId() throws Exception {
                String token = getTOKEN();
                OwnCard addedOwnCard = addOwnCardBeforeTest(token);
                MvcResult result = mockMvc.perform(get("/ownCard/getOwnCard?ownCardId=" + addedOwnCard.getOwnCardId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        // @Test
        // @Transactional
        // @Rollback(value = true)
        // @DisplayName("File: OwnCardController Method: addNewOwnCard")
        // public void ownAnotherCard() throws Exception {
        // MvcResult result = mockMvc
        // .perform(get("/ownCard/addOwnCard?userId=12&cardId=10").contentType(MediaType.APPLICATION_JSON_VALUE))
        // .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        // System.out.println(result.getResponse().getContentAsString());
        // }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: addNewOwnCard")
        public void addNewOwnCard() throws Exception {
                String token = getTOKEN();
                User addedUser = addUserBeforeTest(token);
                Card addedCard = addCardBeforeTest(token);
                // System.out.println(body);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders
                                                .post("/ownCard/addOwnCard?userId=" + addedUser.getUserId()
                                                                + "&cardId=" + addedCard.getCardId())
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: updateOwnCard")
        public void updateOwnCard() throws Exception {
                String token = getTOKEN();
                OwnCard addedOwnCard = addOwnCardBeforeTest(token);

                Timestamp start = new Timestamp(System.currentTimeMillis());
                addedOwnCard.setAccquireDate(start);

                JSONObject updateOwnCard = new JSONObject();
                updateOwnCard.put("OwnCard", addedOwnCard);
                updateOwnCard.put("userName", userService.getOneUser(addedOwnCard.getUserId()).getUserName());
                String body = updateOwnCard.toString();
                System.out.println(body);
                MvcResult result = mockMvc
                                .perform(get("/ownCard/updateOwnCard").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(body).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: addExp")
        public void addExp() throws Exception {
                String token = getTOKEN();
                OwnCard OwnCard = addOwnCardBeforeTest(token);
                MvcResult result = mockMvc
                                .perform(get("/ownCard/addExp?userId=" + OwnCard.getUserId() + "&cardId=" + OwnCard.getCardId()
                                                + "&exp=1").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        // @Test
        // @Transactional
        // @Rollback(value = true)
        // @DisplayName("File: OwnCardController Method: cardLevelUp")
        // public void cardLevelUp() throws Exception {
        // String token = getTOKEN();
        // OwnCard addedOwnCard = addOwnCardBeforeTest();
        // MvcResult result = mockMvc
        // .perform(get("/ownCard/cardLevelUp?userId=" + addedOwnCard.getUserId()+
        // "&cardId=" + addedOwnCard.getCardId()
        // ).contentType(MediaType.APPLICATION_JSON_VALUE)
        // .header("Authorization", token))
        // .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        // System.out.println(result.getResponse().getContentAsString());
        // }

        // @Test
        // @Transactional
        // @Rollback(value = true)
        // @DisplayName("File: OwnCardController Method: ownAnotherCard")
        // public void ownAnotherCard() throws Exception {
        // MvcResult result = mockMvc
        // .perform(get("/ownCard/ownAnotherCard?userId=12&cardId=10")
        // .contentType(MediaType.APPLICATION_JSON_VALUE))
        //
        // .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
        // .andReturn();
        // System.out.println(result.getResponse().getContentAsString());
        // }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: getAllOwnCards")
        public void getAllOwnCards() throws Exception {
                String token = getTOKEN();
                MvcResult result = mockMvc
                                .perform(get("/ownCard/getAllOwnCards").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: getAllOwnCardsByUserName")
        public void getAllOwnCardsByUserName() throws Exception {
                String token = getTOKEN();
                OwnCard addedOwnCard = addOwnCardBeforeTest(token);
                MvcResult result = mockMvc
                                .perform(get("/ownCard/getAllOwnCardsByUserId?userId="
                                                + addedOwnCard.getUserId())
                                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: deleteOwnCards")
        public void deleteOwnCards() throws Exception {
                String token = getTOKEN();
                OwnCard addedOwnCard = addOwnCardBeforeTest(token);
                MvcResult result = mockMvc
                                .perform(get("/ownCard/deleteOwnCards?ownCardIds=" + addedOwnCard.getOwnCardId())
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: deleteAllOwnCards")
        public void deleteAllOwnCards() throws Exception {
                String token = getTOKEN();
                MvcResult result = mockMvc
                                .perform(get("/ownCard/deleteAllOwnCards").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnCardController Method: deleteOwnCard")
        public void deleteOwnCard() throws Exception {
                String token = getTOKEN();
                OwnCard addedOwnCard = addOwnCardBeforeTest(token);
                MvcResult result = mockMvc
                                .perform(get("/ownCard/deleteOwnCard?userId=" + addedOwnCard.getUserId() + "&cardId="
                                                + addedOwnCard.getCardId())
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
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ownCard/List").content(body)
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}