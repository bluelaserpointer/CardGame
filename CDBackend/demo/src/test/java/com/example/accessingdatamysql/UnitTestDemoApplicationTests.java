package com.example.accessingdatamysql;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// @WebAppConfiguration
public class UnitTestDemoApplicationTests {

    // @Before
    // public void init() {
    // System.out.println("Start Unit Testing:");
    // }
    @Autowired
    AccessingDataMysqlApplication accessingDataMysqlApplication;

    // @Test
    // public void testA() {
    // }
    // @After
    // public void after() {
    // System.out.println("End Unit Testing.");
    // }

    @Test
    public void contextLoads() {
        assertThat(accessingDataMysqlApplication).isNotNull();
    }

}