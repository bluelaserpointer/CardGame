// package com.example.accessingdatamysql.GeneralTests.Controller;

// import static org.assertj.core.api.Assertions.assertThat;

// import com.example.accessingdatamysql.controller.EnemyController;

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
// public class EnemyControllerTest {

// @Test
// @DisplayName("File: EnemyController Method: contextLoads")
// public void contextLoads() {
// assertThat(enemyController).isNotNull();
// }

// private MockMvc mockMvc;

// @Autowired
// private EnemyController enemyController;

// @Before
// public void setUp() {
// mockMvc = MockMvcBuilders.standaloneSetup(enemyController).build();
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
// @DisplayName("File: EnemyController Method: findEnemyByEnemyId")
// public void findEnemyByEnemyId() throws Exception {
// MvcResult result = mockMvc
// .perform(get("/enemy/getEnemy?enemyId=77")
// .contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: EnemyController Method: addNewEnemy")
// public void addNewEnemy() throws Exception {
// MvcResult result = mockMvc.perform(get(
// "/enemy/addEnemy?enemyName=EnemyAddTest&healthPoint=2&attack=1&defense=1&attackRange=1&cd=1.0&speed=1&enemyImg=EnemyAddTest&shortDescription=EnemyAddTest&enemyDescription=EnemyAddTest")
// .contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: EnemyController Method: updateEnemy")
// public void updateEnemy() throws Exception {
// MvcResult result = mockMvc.perform(get(
// "/enemy/updateEnemy?enemyId=77&enemyName=EnemyAddTest&healthPoint=100&attack=1&defense=1&attackRange=1&cd=1.0&speed=1&enemyImg=EnemyAddTest&shortDescription=EnemyAddTest&enemyDescription=EnemyAddTest")
// .contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: EnemyController Method: getAllEnemies")
// public void getAllEnemies() throws Exception {
// MvcResult result = mockMvc
// .perform(get("/enemy/getAllEnemies").contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: EnemyController Method: deleteEnemies")
// public void deleteEnemies() throws Exception {
// MvcResult result = mockMvc
// .perform(get("/enemy/deleteEnemies?enemyIds=77")
// .contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: EnemyController Method: deleteAllEnemies")
// public void deleteAllEnemies() throws Exception {
// MvcResult result = mockMvc
// .perform(get("/enemy/deleteAllEnemies").contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// @Test
// @Transactional
// @Rollback(value = true)
// @DisplayName("File: EnemyController Method: deleteEnemy")
// public void deleteEnemy() throws Exception {
// MvcResult result = mockMvc
// .perform(get("/enemy/deleteEnemy?enemyId=77")
// .contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
// .andReturn();
// System.out.println(result.getResponse().getContentAsString());
// }

// }