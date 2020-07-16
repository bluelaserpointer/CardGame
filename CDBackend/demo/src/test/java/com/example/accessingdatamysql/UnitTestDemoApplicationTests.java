package com.example.accessingdatamysql;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// @WebAppConfiguration
public class UnitTestDemoApplicationTests {

    // @Before
    // public void init() {
    // System.out.println("Start Unit Testing:");
    // }
    @Test
    public void testA() {
        System.out.println("Hello");
    }
    // @After
    // public void after() {
    // System.out.println("End Unit Testing.");
    // }

    @Test
    public void contextLoads() {

    }

}