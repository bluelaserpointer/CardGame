package com.example.accessingdatamysql.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.Security.JwtUtil;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.example.accessingdatamysql.service.MailBoxService;

// import javax.validation.constraints.Null;

import com.example.accessingdatamysql.service.UserService;

@CrossOrigin(origins = "*")
@RestController() // This means that this class is a Controller
@RequestMapping(value = "/user") // This means URL's start with /demo (after Application path)
@EnableAutoConfiguration
public class UserController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;

  @Autowired
  private MailBoxService mailBoxService;

  @Autowired
  private UserLoginRecordService userLoginRecordService;

  // private BCryptPasswordEncoder bCryptPasswordEncoder;

  // 获取一个用户信息
  @GetMapping(value = "/getUser")
  public @ResponseBody User findUserByUserId(@RequestParam("userId") Integer userId) {
    System.out.println("Class: UserController Method:finduserByUserId Param:" + userId);
    return userService.getOneUser(userId);
  }

  // 用用户名获取用户
  @RequestMapping(value = "/getUserByUserName")
  public @ResponseBody User findUserByUserName(@RequestParam("userName") String userName) {
    return userService.getOneUserByUserName(userName);
  }

  // 注册用户
  @PostMapping("/register")
  public @ResponseBody String register(@RequestBody User registerUser) {
    final User existedUser = userService.getOneUserByUserName(registerUser.getUserName());
    final JSONObject response = new JSONObject();
    if(existedUser != null) {
      response.put("failReason", "用户名已存在");
    } else {
      registerUser.setIdentity(User.ROLE_USER);
      response.put("user", userService.addNewUser(registerUser));
    }
    return response.toString();
  }

  // 更新一个用户信息
  @RequestMapping(value = "/updateUser")
  public @ResponseBody User updateUser(@RequestBody User updateUser) {
    return userService.updateUser(updateUser);
  }

  // 获取所有用户信息
  @RequestMapping(value = "/getAllUsers")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  // 删除部分用户
  @RequestMapping(value = "/deleteUsers")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteUsers(@RequestParam("userIds") List<Integer> userIds) {
    return userService.deleteUsers(userIds);
  }

  // 删除所有用户
  @RequestMapping(value = "/deleteAllUsers")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteAll() {
    return userService.deleteAll();
  }

  // 确认用户身份
  @RequestMapping(value = "/confirmDelete")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Integer confirmDelete(@RequestParam("userName") String userName, @RequestParam("password") String password) {
    return userService.identifyUser(userName, password);
  }

  // 登录逻辑
  @PostMapping("/login")
  public String identifyUser(@RequestBody AuthRequest authRequest) {
    final String userName = authRequest.getUserName(), password = authRequest.getPassword();
    System.out.print("UserController: login request: " + userName + ", " + password + " -> ");
    final User user = userService.getOneUserByUserName(userName);
    final JSONObject response = new JSONObject();
    if(user == null) {
      System.out.println("refused(wrong username)");
      response.put("failReason", "用户名或密码错误");
    } else if (!user.getPassword().equals(password)) {
      System.out.println("refused(wrong password)");
      response.put("failReason", "用户名或密码错误");
    } else if(!user.getAccess()) {
      System.out.println("refused(banned user)");
      response.put("failReason", "用户已被禁止登录，详见游戏官网");
    } else {
      System.out.println("accepted");
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
      userLoginRecordService.userLogin(user.getUserId());
      response.put("token", jwtUtil.generateToken(userName));
      response.put("user", user);
    }
    return response.toString();
  }

  @RequestMapping(value = "/logout")
  public void userLogout(@RequestParam("userId") Integer userId, @RequestParam("type") Integer type) {
    // Type: 1 代表正常退出， 2 代表系统IDLE踢出
    userLoginRecordService.userLogout(userId, type);
  }

  // 删除一个指定用户
  @RequestMapping(value = "/deleteUser")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<User> deleteUser(@RequestParam("userId") Integer userId) {
    return userService.deleteUser(userId);
  }

  // 增加用户经验值(如果累计经验值超过升级所需经验值则升级后再返回user)
  @RequestMapping(value = "/addExp")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody User addExp(@RequestParam("userId") Integer userId, @RequestParam("exp") Integer exp) {
    System.out.println("Class: UserController Method: addExp Param: userId = " + userId + " exp = " + exp);
    return userService.addExp(userId, exp);
  }

  // 获取用户的游戏信箱
  @RequestMapping(value = "/getMailBox")
  public @ResponseBody List<Mail> getUserMailBox(@RequestParam("userId") Integer userId) {
    return mailBoxService.getOneUserMails(userId);
  }
}
