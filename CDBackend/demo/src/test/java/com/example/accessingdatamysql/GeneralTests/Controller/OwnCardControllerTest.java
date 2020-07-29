package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
import com.example.accessingdatamysql.controller.OwnCardController;
import com.example.accessingdatamysql.entity.AuthRequest;
import com.example.accessingdatamysql.entity.Card;
import com.example.accessingdatamysql.entity.CardDetails;
import com.example.accessingdatamysql.entity.OwnCard;
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

    public OwnCard addOwnCardBeforeTest() throws Exception {
        String token = getTOKEN();

        // System.out.println(body);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/ownItem/addOwnCard?userId=1&cardId=1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());

        // System.out.println(result.getResponse().getContentAsString());
        String json = result.getResponse().getContentAsString();
        OwnCard OwnCard = JSON.parseObject(json, OwnCard.class);
        return OwnCard;
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
                .perform(get("/card/addCard").contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());

        String json = result.getResponse().getContentAsString();
        card = JSON.parseObject(json, Card.class);
        return card;
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnCardController Method: findOwnCardByOwnCardId")
    public void findOwnCardByOwnCardId() throws Exception {
        String token = getTOKEN();
        OwnCard addedOwnCard = addOwnCardBeforeTest();
        MvcResult result = mockMvc
                .perform(get("/ownCard/getOwnCard?ownCardId=" + addedOwnCard.getOwnCardId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
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

        // System.out.println(body);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/ownCard/addOwnCard?userId=1&cardId=1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnCardController Method: updateOwnCard")
    public void updateOwnCard() throws Exception {
        String token = getTOKEN();
        OwnCard addedOwnCard = addOwnCardBeforeTest();

        Timestamp start = new Timestamp(System.currentTimeMillis());
        OwnCard OwnCard = new OwnCard(2, 2, start);
        OwnCard.setOwnCardId(addedOwnCard.getOwnCardId());

        String body = JSON.toJSONString(OwnCard);
        MvcResult result = mockMvc
                .perform(get("/ownCard/updateOwnCard").contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnCardController Method: cardLevelUp")
    public void cardLevelUp() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc
                .perform(get("/ownCard/cardLevelUp?userId=12&cardId=10").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

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
        MvcResult result = mockMvc.perform(get("/ownCard/getAllOwnCards").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnCardController Method: getAllOwnCardsByUserId")
    public void getAllOwnCardsByUserId() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc
                .perform(get("/ownCard/getAllOwnCardsByUserId?userId=12").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnCardController Method: deleteOwnCards")
    public void deleteOwnCards() throws Exception {
        String token = getTOKEN();
        OwnCard addedOwnCard = addOwnCardBeforeTest();
        MvcResult result = mockMvc
                .perform(get("/ownCard/deleteOwnCards?ownCardIds=88").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnCardController Method: deleteAllOwnCards")
    public void deleteAllOwnCards() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc
                .perform(get("/ownCard/deleteAllOwnCards").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnCardController Method: deleteOwnCard")
    public void deleteOwnCard() throws Exception {
        String token = getTOKEN();
        OwnCard addedOwnCard = addOwnCardBeforeTest();
        MvcResult result = mockMvc
                .perform(
                        get("/ownCard/deleteOwnCard?userId=12&cardId=10").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}