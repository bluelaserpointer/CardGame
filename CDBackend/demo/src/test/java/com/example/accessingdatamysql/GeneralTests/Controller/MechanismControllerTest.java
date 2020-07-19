package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.accessingdatamysql.controller.MechanismController;

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
public class MechanismControllerTest {

        @Test
        @DisplayName("File: MechanismController Method: contextLoads")
        public void contextLoads() {
                assertThat(mechanismController).isNotNull();
        }

        private MockMvc mockMvc;

        @Autowired
        private MechanismController mechanismController;

        @Before
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(mechanismController).build();
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
        @DisplayName("File: MechanismController Method: drawCard")
        public void drawCard() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/mechanism/drawCard?chi=10&mat=10&eng=10")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}