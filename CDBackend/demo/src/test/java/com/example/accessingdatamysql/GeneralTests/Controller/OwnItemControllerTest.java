package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.accessingdatamysql.controller.OwnItemController;

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
public class OwnItemControllerTest {

        @Test
        @DisplayName("File: OwnItemController Method: contextLoads")
        public void contextLoads() {
                assertThat(ownItemController).isNotNull();
        }

        private MockMvc mockMvc;

        @Autowired
        private OwnItemController ownItemController;

        @Before
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(ownItemController).build();
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
        @DisplayName("File: OwnItemController Method: findOwnItemByOwnItemId")
        public void findOwnItemByOwnItemId() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownItem/getOwnItem?ownItemId=92")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnItemController Method: addNewOwnItem")
        public void repeatOwnItem() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownItem/addOwnItem?userId=12&itemId=10&itemCount=3")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnItemController Method: addNewOwnItem")
        public void addNewOwnItem() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownItem/addOwnItem?userId=12&itemId=100&itemCount=3")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnItemController Method: updateOwnItem")
        public void updateOwnItem() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownItem/updateOwnItem?ownItemId=92&userId=12&itemId=10&itemCount=4")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnItemController Method: getAllOwnItems")
        public void getAllOwnItems() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownItem/getAllOwnItems").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnItemController Method: deleteOwnItems")
        public void deleteOwnItems() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownItem/deleteOwnItems?ownItemIds=92")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnItemController Method: deleteAllOwnItems")
        public void deleteAllOwnItems() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownItem/deleteAllOwnItems")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: OwnItemController Method: deleteOwnItem")
        public void deleteOwnItem() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/ownItem/deleteOwnItem?userId=12&itemId=10")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}