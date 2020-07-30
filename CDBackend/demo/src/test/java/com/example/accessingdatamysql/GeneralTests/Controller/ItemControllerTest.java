package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
import com.example.accessingdatamysql.controller.ItemController;
import com.example.accessingdatamysql.entity.AuthRequest;
import com.example.accessingdatamysql.entity.Item;
import com.example.accessingdatamysql.entity.ItemDetails;
import com.example.accessingdatamysql.entity.User;

import org.aspectj.internal.lang.annotation.ajcITD;
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
public class ItemControllerTest {

    // @Test
    // @DisplayName("File: ItemController Method: contextLoads")
    // public void contextLoads() {
    // assertThat(itemController).isNotNull();
    // }

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    // @Autowired
    // private ItemController itemController;

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

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ItemController Method: findItemByItemId")
    public void findItemByItemId() throws Exception {
        String token = getTOKEN();
        Item Item = addItemBeforeTest();
        MvcResult result = mockMvc
                .perform(get("/item/getItem?itemId=" + Item.getItemId()).contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ItemController Method: addNewItem")
    public void addNewItem() throws Exception {
        String token = getTOKEN();
        Item Item = new Item("addNewItem", 1);
        ItemDetails ItemDetails = new ItemDetails();
        ItemDetails.setItemDescription("addNewItem");
        ItemDetails.setItemImg("addNewItem");
        Item.setItemDetails(ItemDetails);

        String body = JSON.toJSONString(Item);

        // System.out.println(body);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/item/addItem").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ItemController Method: updateItem")
    public void updateItem() throws Exception {

        String token = getTOKEN();
        Item addedItem = addItemBeforeTest();
        Item Item = new Item("updateItem", 1);
        ItemDetails ItemDetails = new ItemDetails(addedItem.getItemId(), "updateItem", "updateItem");
        Item.setItemDetails(ItemDetails);
        Item.setItemId(addedItem.getItemId());

        String body = JSON.toJSONString(Item);
        MvcResult result = mockMvc
                .perform(get("/item/updateItem?itemId=" + Item.getItemId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(body).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ItemController Method: getAllItems")
    public void getAllItems() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc
                .perform(get("/item/getAllItems").contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization",
                        token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ItemController Method: deleteItems")
    public void deleteItems() throws Exception {
        String token = getTOKEN();
        Item Item = addItemBeforeTest();
        MvcResult result = mockMvc
                .perform(get("/item/deleteItems?itemIds=" + Item.getItemId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ItemController Method: deleteAllItems")
    public void deleteAllItems() throws Exception {
        String token = getTOKEN();
        MvcResult result = mockMvc
                .perform(get("/item/deleteAllItems").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: ItemController Method: deleteItem")
    public void deleteItem() throws Exception {
        String token = getTOKEN();
        Item Item = addItemBeforeTest();
        MvcResult result = mockMvc
                .perform(get("/item/deleteItem?itemId=" + Item.getItemId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}