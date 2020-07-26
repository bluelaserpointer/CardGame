// package com.example.accessingdatamysql.GeneralTests.Controller;

// import static org.assertj.core.api.Assertions.assertThat;

// import com.example.accessingdatamysql.controller.ItemController;

// import org.junit.Before;
// import org.junit.Test;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.annotation.Rollback;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.transaction.annotation.Transactional;

// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

// @RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest
// @AutoConfigureMockMvc
// public class ItemControllerTest {

// @Test
// @DisplayName("File: ItemController Method: contextLoads")
// public void contextLoads() {
// assertThat(itemController).isNotNull();
// }

// private MockMvc mockMvc;

// @Autowired
// private ItemController itemController;

// @Before
// public void setUp() {
// mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
// }

// @AfterEach
// void tearDown() {

// }

// @AfterAll
// static void tearDownAll() {

// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: ItemController Method: findItemByItemId")
// public void findItemByItemId() throws Exception {
// MvcResult result = mockMvc
// .perform(get("/item/getItem?itemId=79").contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: ItemController Method: addNewItem")
// public void addNewItem() throws Exception {
// MvcResult result = mockMvc.perform(get(
// "/item/addItem?itemName=ItemAddTest&price=10&itemImg=ItemAddTest&itemDescription=ItemAddTest")
// .contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: ItemController Method: updateItem")
// public void updateItem() throws Exception {
// MvcResult result = mockMvc.perform(get(
// "/item/updateItem?itemId=79&itemName=ItemAddTest&price=10&itemImg=ItemAddTest&itemDescription=ItemAddTest")
// .contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: ItemController Method: getAllItems")
// public void getAllItems() throws Exception {
// MvcResult result = mockMvc
// .perform(get("/item/getAllItems").contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: ItemController Method: deleteItems")
// public void deleteItems() throws Exception {
// MvcResult result = mockMvc
// .perform(get("/item/deleteItems?itemIds=79")
// .contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: ItemController Method: deleteAllItems")
// public void deleteAllItems() throws Exception {
// MvcResult result = mockMvc
// .perform(get("/item/deleteAllItems").contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: ItemController Method: deleteItem")
// public void deleteItem() throws Exception {
// MvcResult result = mockMvc
// .perform(get("/item/deleteItem?itemId=79")
// .contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// }