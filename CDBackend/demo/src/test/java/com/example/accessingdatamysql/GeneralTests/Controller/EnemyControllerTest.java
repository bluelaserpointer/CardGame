package com.example.accessingdatamysql.GeneralTests.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.AuthRequest;
import com.example.accessingdatamysql.entity.Enemy;
import com.example.accessingdatamysql.entity.EnemyDetails;
import com.example.accessingdatamysql.entity.ListRequest;
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
public class EnemyControllerTest {

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
        public Enemy addEnemyBeforeTest(String token) throws Exception {
                Enemy enemy = new Enemy("addEnemyBeforeTest", 1, 1, 1, 1, 1.0, 1);
                EnemyDetails enemyDetails = new EnemyDetails();
                enemyDetails.setEnemyDescription("addEnemyBeforeTest");
                enemyDetails.setEnemyImg("addEnemyBeforeTest");
                enemyDetails.setShortDescription("addEnemyBeforeTest");
                enemy.setEnemyDetails(enemyDetails);

                String body = JSON.toJSONString(enemy);

                // System.out.println(body);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("/enemy/addEnemy")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());

                // System.out.println(result.getResponse().getContentAsString());
                String json = result.getResponse().getContentAsString();
                enemy = JSON.parseObject(json, Enemy.class);
                return enemy;
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: EnemyController Method: findEnemyByEnemyId")
        public void findEnemyByEnemyId() throws Exception {
                String token = getTOKEN();
                Enemy addedEnemy = addEnemyBeforeTest(token);
                MvcResult result = mockMvc.perform(get("/enemy/getEnemy?enemyId=" + addedEnemy.getEnemyId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: EnemyController Method: addNewEnemy")
        public void addNewEnemy() throws Exception {

                String token = getTOKEN();
                Enemy enemy = new Enemy("addNewEnemy", 1, 1, 1, 1, 1.0, 1);
                EnemyDetails enemyDetails = new EnemyDetails();
                enemyDetails.setEnemyDescription("addNewEnemy");
                enemyDetails.setEnemyImg("addNewEnemy");
                enemyDetails.setShortDescription("addNewEnemy");
                enemy.setEnemyDetails(enemyDetails);

                String body = JSON.toJSONString(enemy);

                // System.out.println(body);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("/enemy/addEnemy")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: EnemyController Method: updateEnemy")
        public void updateEnemy() throws Exception {
                String token = getTOKEN();
                Enemy addedEnemy = addEnemyBeforeTest(token);
                Enemy enemy = new Enemy("updateEnemy", 1, 1, 1, 1, 1.0, 1);
                EnemyDetails enemyDetails = new EnemyDetails(enemy.getEnemyId(), "updateEnemy", "updateEnemy",
                                "updateEnemy");
                enemy.setEnemyDetails(enemyDetails);
                enemy.setEnemyId(addedEnemy.getEnemyId());

                String body = JSON.toJSONString(enemy);
                MvcResult result = mockMvc
                                .perform(get("/enemy/updateEnemy?enemyId=" + addedEnemy.getEnemyId())
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: EnemyController Method: getAllEnemies")
        public void getAllEnemies() throws Exception {
                String token = getTOKEN();
                MvcResult result = mockMvc
                                .perform(get("/enemy/getAllEnemies").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: EnemyController Method: deleteEnemies")
        public void deleteEnemies() throws Exception {
                String token = getTOKEN();
                Enemy addedEnemy = addEnemyBeforeTest(token);
                MvcResult result = mockMvc.perform(get("/enemy/deleteEnemies?enemyIds=" + addedEnemy.getEnemyId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: EnemyController Method: deleteAllEnemies")
        public void deleteAllEnemies() throws Exception {
                String token = getTOKEN();
                MvcResult result = mockMvc
                                .perform(get("/enemy/deleteAllEnemies").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: EnemyController Method: deleteEnemy")
        public void deleteEnemy() throws Exception {
                String token = getTOKEN();
                Enemy addedEnemy = addEnemyBeforeTest(token);
                MvcResult result = mockMvc.perform(get("/enemy/deleteEnemy?enemyId=" + addedEnemy.getEnemyId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
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
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/enemy/List").content(body)
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}