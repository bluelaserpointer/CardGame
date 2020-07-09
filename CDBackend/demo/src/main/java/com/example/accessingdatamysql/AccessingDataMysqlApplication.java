package com.example.accessingdatamysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.accessingdatamysql.repository")
@SpringBootApplication
public class AccessingDataMysqlApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccessingDataMysqlApplication.class, args);
    // ApplicationContext context =
    // SpringApplication.run(AccessingDataMysqlApplication.class, args);

  }

}