package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.accessingdatamysql.controller.AdminController;

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
public class AdminControllerTest {

    @Test
    @DisplayName("File: AdminController Method: contextLoads")
    public void contextLoads() {
        assertThat(adminController).isNotNull();
    }

    private MockMvc mockMvc;

    @Autowired
    private AdminController adminController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
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
    @DisplayName("File: AdminController Method: findAdminByAdminId")
    public void findAdminByAdminId() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/admin/getAdmin?adminId=66").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: AdminController Method: addNewAdmin")
    public void addNewAdmin() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/admin/addAdmin?adminName=adminAddTest&password=adminAddTest&role=1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: AdminController Method: updateAdmin")
    public void updateAdmin() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/admin/updateAdmin?adminId=66&adminName=adminAddTest&password=adminAddTest&role=2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: AdminController Method: getAllAdmins")
    public void getAllAdmins() throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/getAllAdmins").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: AdminController Method: deleteAdmins")
    public void deleteAdmins() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/admin/deleteAdmins?adminIds=66").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: AdminController Method: deleteAllAdmins")
    public void deleteAllAdmins() throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/deleteAllAdmins").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: AdminController Method: deleteAdmin")
    public void deleteAdmin() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/admin/deleteAdmin?adminId=66").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: AdminController Method: identifyAdmin")
    public void identifyAdmin() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/admin/identifyAdmin?adminName=adminAddTest&password=adminAddTest")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: AdminController Method: getAdminNames")
    public void getAdminNames() throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/getAdminName").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("File: AdminController Method: getAdminRole")
    public void getAdminRole() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        get("/admin/getAdminRole?adminName=adminAddTest").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}