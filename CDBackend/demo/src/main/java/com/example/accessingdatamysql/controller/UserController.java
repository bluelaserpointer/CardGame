package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.Security.JwtUtil;
import com.example.accessingdatamysql.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;
  // private BCryptPasswordEncoder bCryptPasswordEncoder;

  // 获取一个用户信息
  @GetMapping(value = "/getUser")
  public @ResponseBody User findUserByUserId(@RequestParam("userId") Integer userId) {
    System.out.println("Class: UserController Method:finduserByUserId Param:" + userId);
    return userService.getOneUser(userId);
  }

  // 添加一个新用户
  // @RequestMapping(value = "/addUser")
  // public @ResponseBody User addNewUser(@RequestParam("userName") String
  // userName, @RequestParam("email") String email,
  // @RequestParam("password") String password, @RequestParam("phoneNumber")
  // String phoneNumber) {
  // // 加密密码
  // // password = bCryptPasswordEncoder.encode(password);
  // return userService.addNewUser(userName, email, password, phoneNumber);
  // }

  @PostMapping("/register")
  public @ResponseBody User identifyUser(@RequestBody User registerUser) {
    return userService.addNewUser(registerUser.getUserName(), registerUser.getEmail(), registerUser.getPassword(),
        registerUser.getPhoneNumber());
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
    // 加密密码
    // password = bCryptPasswordEncoder.encode(password);
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
  @PostMapping("/identifyUser")
  public String identifyUser(@RequestBody AuthRequest authRequest) {
    try {
      authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
    } catch (Exception ex) {
      throw ex;
    }
    return jwtUtil.generateToken(authRequest.getUserName());
  }

  // 删除一个指定用户
  @RequestMapping(value = "/deleteUser")
  public List<User> deleteUser(@RequestParam("userId") Integer userId) {
    return userService.deleteUser(userId);
  }

  // 增加用户经验值(如果累计经验值超过升级所需经验值则升级后再返回user)
  @RequestMapping(value = "/addExp")
  public @ResponseBody User addExp(@RequestParam("userId") Integer userId, @RequestParam("exp") Integer exp) {
    System.out.println("Class: UserController Method: addExp Param: userId = " + userId + " exp = " + exp);
    return userService.addExp(userId, exp);
  }
}
