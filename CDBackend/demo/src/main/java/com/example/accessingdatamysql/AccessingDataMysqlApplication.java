package com.example.accessingdatamysql;

import java.util.List;

import javax.annotation.PostConstruct;

import com.example.accessingdatamysql.entity.User;
import com.example.accessingdatamysql.repository.UserRepository;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJpaRepositories(basePackages = "com.example.accessingdatamysql.repository")
@EnableScheduling
@SpringBootApplication
public class AccessingDataMysqlApplication {

  // @Bean
  // public PasswordEncoder passwordEncoder() {
  // return new BCryptPasswordEncoder();
  // }

  @Autowired
  private UserRepository repository;

  // @PostConstruct
  // public void initUsers() {
  // // List<User> users = Stream.of(
  // User u = new User("javatechie", "javatechie", "password",
  // "javatechie@gmail.com");
  // // ,
  // // new User("javatechie", "user1", "pwd1", "user1@gmail.com"),
  // // new User("javatechie", "user2", "pwd2", "user2@gmail.com"),
  // // new User("javatechie", "user3", "pwd3",
  // // "user3@gmail.com")).collect(Collectors.toList());
  // repository.save(u);
  // }

  public static void main(String[] args) {
    SpringApplication.run(AccessingDataMysqlApplication.class, args);
    // ApplicationContext context =
    // SpringApplication.run(AccessingDataMysqlApplication.class, args);

  }

}
