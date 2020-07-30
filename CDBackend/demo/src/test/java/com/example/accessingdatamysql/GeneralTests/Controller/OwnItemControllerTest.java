package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
import com.example.accessingdatamysql.controller.OwnItemController;
import com.example.accessingdatamysql.entity.AuthRequest;
import com.example.accessingdatamysql.entity.Item;
import com.example.accessingdatamysql.entity.ItemDetails;
import com.example.accessingdatamysql.entity.OwnItem;
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

import java.sql.Timestamp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OwnItemControllerTest {

    // @Test
    // @DisplayName("File: OwnItemController Method: contextLoads")
    // public void contextLoads() {
    // assertThat(ownItemController).isNotNull();
    // }

    // private MockMvc mockMvc;

    // @Autowired
    // private OwnItemController ownItemController;

    // @Before
    // public void setUp() {
    // mockMvc = MockMvcBuilders.standaloneSetup(ownItemController).build();
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

    public OwnItem addOwnItemBeforeTest() throws Exception {
        String token = getTOKEN();
        User addedUser = addUserBeforeTest();
        Item addedItem = addItemBeforeTest();
        Timestamp start = new Timestamp(System.currentTimeMillis());
        // System.out.println(body);
        OwnItem OwnItem = new OwnItem(addedUser.getUserId(), addedItem.getItemId(), 1, start);
        String body = JSON.toJSONString(OwnItem);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/ownItem/addOwnItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(body).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());

        // System.out.println(result.getResponse().getContentAsString());
        String json = result.getResponse().getContentAsString();
        OwnItem = JSON.parseObject(json, OwnItem.class);
        return OwnItem;
    }

    public Item addItemBeforeTest() throws Exception {
        String token = getTOKEN();
        Item Item = new Item("addItemBeforeTest", 1);
        ItemDetails ItemDetails = new ItemDetails();
        ItemDetails.setItemDescription("addItemBeforeTest");
        ItemDetails.setItemImg("addItemBeforeTest");
        Item.setItemDetails(ItemDetails);

        String body = JSON.toJSONString(Item);

        // System.out.println(body);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/item/addItem").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());

        // System.out.println(result.getResponse().getContentAsString());
        String json = result.getResponse().getContentAsString();
        Item = JSON.parseObject(json, Item.class);
        return Item;
    }

    public User addUserBeforeTest() throws Exception {
        String token = getTOKEN();
        User User = new User("addUserBeforeTest", "addUserBeforeTest", "addUserBeforeTest", "addUserBeforeTest",
                "addUserBeforeTest");

        String body = JSON.toJSONString(User);

        // System.out.println(body);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/user/register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());

        // System.out.println(result.getResponse().getContentAsString());
        String json = result.getResponse().getContentAsString();
        User = JSON.parseObject(json, User.class);
        return User;
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnItemController Method: findOwnItemByOwnItemId")
    public void findOwnItemByOwnItemId() throws Exception {
        String token = getTOKEN();
        OwnItem OwnItem = addOwnItemBeforeTest();
        MvcResult result = mockMvc
                .perform(get("/ownItem/getOwnItem?ownItemId=" + OwnItem.getOwnItemId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    // @Test
    // @Transactional
    // @Rollback(value = true)
    // @DisplayName("File: OwnItemController Method: addNewOwnItem")
    // public void repeatOwnItem() throws Exception {
    // String token = getTOKEN();
    // MvcResult result = mockMvc
    // .perform(get("/ownItem/addOwnItem?userId=12&itemId=10&itemCount=3")
    // .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization",
    // token))
    // .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    // System.out.println(result.getResponse().getContentAsString());
    // }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnItemController Method: addNewOwnItem")
    public void addNewOwnItem() throws Exception {
        String token = getTOKEN();
        User addedUser = addUserBeforeTest();
        Item addedItem = addItemBeforeTest();
        Timestamp start = new Timestamp(System.currentTimeMillis());
        // System.out.println(body);
        OwnItem OwnItem = new OwnItem(addedUser.getUserId(), addedItem.getItemId(), 1, start);
        String body = JSON.toJSONString(OwnItem);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/ownItem/addOwnItem")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(body).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnItemController Method: updateOwnItem")
    public void updateOwnItem() throws Exception {
        String token = getTOKEN();
        OwnItem OwnItem = addOwnItemBeforeTest();
        Timestamp start = new Timestamp(System.currentTimeMillis());
        OwnItem.setAccquireDate(start);
        String body = JSON.toJSONString(OwnItem);

        MvcResult result = mockMvc
                .perform(get("/ownItem/updateOwnItem").contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnItemController Method: getAllOwnItems")
    public void getAllOwnItems() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc
                .perform(get("/ownItem/getAllOwnItems").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnItemController Method: deleteOwnItems")
    public void deleteOwnItems() throws Exception {
        String token = getTOKEN();
        OwnItem OwnItem = addOwnItemBeforeTest();
        MvcResult result = mockMvc
                .perform(get("/ownItem/deleteOwnItems?ownItemIds=" + OwnItem.getOwnItemId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnItemController Method: deleteAllOwnItems")
    public void deleteAllOwnItems() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc
                .perform(get("/ownItem/deleteAllOwnItems").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: OwnItemController Method: deleteOwnItem")
    public void deleteOwnItem() throws Exception {
        String token = getTOKEN();
        OwnItem OwnItem = addOwnItemBeforeTest();
        MvcResult result = mockMvc
                .perform(get("/ownItem/deleteOwnItem?userId=" + OwnItem.getUserId() + "&itemId=" + OwnItem.getItemId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}