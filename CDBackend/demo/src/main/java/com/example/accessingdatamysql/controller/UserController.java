package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// import javax.validation.constraints.Null;

import com.example.accessingdatamysql.service.UserService;

@CrossOrigin(origins = "*")
@RestController() // This means that this class is a Controller
@RequestMapping(value = "/user") // This means URL's start with /demo (after Application path)
public class UserController {

  @Autowired
  private UserService userService;

  // 获取一个用户信息
  @GetMapping(value = "/getUser")
  public User findUserByUserId(@RequestParam("userId") Integer userId) {
    return userService.getOneUser(userId);
  }

  // 添加一个新用户
  @RequestMapping(value = "/addUser")
  public @ResponseBody User addNewUser(@RequestParam("userName") String userName, @RequestParam("email") String email,
      @RequestParam("password") String password, @RequestParam("phoneNumber") String phoneNumber) {
    return userService.addNewUser(userName, email, password, phoneNumber);
  }

  // 更新一个用户信息
  @RequestMapping(value = "/updateUser")
  public @ResponseBody User updateUser(@RequestParam("userId") Integer userId,
      @RequestParam("userName") String userName, @RequestParam("email") String email,
      @RequestParam("password") String password, @RequestParam("phoneNumber") String phoneNumber,
      @RequestParam("credits") Integer credits, @RequestParam("access") Boolean access,
      @RequestParam("level") Integer level, @RequestParam("curExpPoint") Integer curExpPoint,
      @RequestParam("stamina") Integer stamina, @RequestParam("money") Integer money,
      @RequestParam("grade") Double grade, @RequestParam("engKnowledge") Integer engKnowledge,
      @RequestParam("mathKnowledge") Integer mathKnowledge, @RequestParam("chiKnowledge") Integer chiKnowledge) {
    return userService.updateUser(userId, userName, email, password, phoneNumber, credits, access, level, curExpPoint,
        stamina, money, grade, engKnowledge, mathKnowledge, chiKnowledge);
  }

  // 获取所有用户信息
  @RequestMapping(value = "/getAllUsers")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  // 删除部分用户
  @RequestMapping(value = "/deleteUsers")
  public String deleteUsers(@RequestParam("userIds") List<Integer> userIds) {
    return userService.deleteUsers(userIds);
  }

  // 删除所有用户
  @RequestMapping(value = "/deleteAllUsers")
  public String deleteAll() {
    return userService.deleteAll();
  }

  // 登录逻辑
  @RequestMapping(value = "/identifyUser")
  public boolean identifyUser(@RequestParam("userName") String userName, @RequestParam("password") String password) {
    return userService.identifyUser(userName, password);
  }

  // 删除一个指定用户
  @RequestMapping(value = "/deleteUser")
  public List<User> deleteUser(@RequestParam("userId") Integer userId) {
    return userService.deleteUser(userId);
  }

  // 增加用户经验值(如果累计经验值超过升级所需经验值则升级后再返回user)
  @RequestMapping(value = "/addExp")
  public @ResponseBody User addExp(@RequestParam("userId") Integer userId, @RequestParam("exp") Integer exp) {
    return userService.addExp(userId, exp);
  }
}
