package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.accessingdatamysql.controller.MailController;

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
public class MailControllerTest {

        @Test
        @DisplayName("File: MailController Method: contextLoads")
        public void contextLoads() {
                assertThat(mailController).isNotNull();
        }

        private MockMvc mockMvc;

        @Autowired
        private MailController mailController;

        @Before
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(mailController).build();
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
        @DisplayName("File: MailController Method: findMailByMailId")
        public void findMailByMailId() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/mail/getMail?mailId=82").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: addNewMail")
        public void addNewMail() throws Exception {
                MvcResult result = mockMvc.perform(get(
                                "/mail/addMail?mailName=MailAddTest&mailImg=MailAddTest&mailDescription=MailAddTest")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: updateMail")
        public void updateMail() throws Exception {
                MvcResult result = mockMvc.perform(get(
                                "/mail/updateMail?mailId=82&mailName=MailAddTest&mailImg=MailAddTest&mailDescription=MailAddTest")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: getAllMails")
        public void getAllMails() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/mail/getAllMails").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: deleteMails")
        public void deleteMails() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/mail/deleteMails?mailIds=82")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: deleteAllMails")
        public void deleteAllMails() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/mail/deleteAllMails").contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: deleteMail")
        public void deleteMail() throws Exception {
                MvcResult result = mockMvc
                                .perform(get("/mail/deleteMail?mailId=82")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}