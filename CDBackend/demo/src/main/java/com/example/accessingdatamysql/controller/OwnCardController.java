package com.example.accessingdatamysql.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import static com.example.accessingdatamysql.GlobalConstants.general_page_size;

import java.sql.Timestamp;
// import java.sql.Timestamp;
import java.util.*;

import com.example.accessingdatamysql.service.OwnCardService;
import com.example.accessingdatamysql.service.UserService;

@CrossOrigin(origins = "*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/ownCard") // This means URL's start with /demo (after Application path)
public class OwnCardController {
  @Autowired
  private OwnCardService OwnCardService;

  @Autowired
  private UserService UserService;

  // 获取一张用户拥有卡牌关系
  @RequestMapping(value = "/getOwnCard")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public OwnCard findOwnCardByOwnCardId(@RequestParam("ownCardId") Integer ownCardId) {
    return OwnCardService.getOneOwnCard(ownCardId);
  }

  // 增加一个用户拥有卡牌关系
  @RequestMapping(value = "/addOwnCard")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody OwnCard addNewOwnCard(@RequestParam("userId") Integer userId,
      @RequestParam("cardId") Integer cardId) {
    return OwnCardService.addNewOwnCard(userId, cardId);
  }

  // 更新一个用户拥有卡牌关系
  @RequestMapping(value = "/updateOwnCard")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody OwnCard updateOwnCard(@RequestBody JSONObject updateOwnCard) {
    return OwnCardService.updateOwnCard(JSON.parseObject(updateOwnCard.getString("OwnCard"), OwnCard.class));
  }

  // 增加用户经验值(如果累计经验值超过升级所需经验值则升级后再返回OwnCard)
  @RequestMapping(value = "/addExp")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody OwnCard addExp(@RequestParam("userId") Integer userId, @RequestParam("cardId") Integer cardId,
      @RequestParam("exp") Integer exp) {
    return OwnCardService.addExp(userId, cardId, exp);
  }

  // 用户再一次拥有已拥有的卡片
  // @RequestMapping(value = "/ownAnotherCard")
  // public @ResponseBody OwnCard ownAnotherCard(@RequestParam("userId") Integer
  // userId,
  // @RequestParam("cardId") Integer cardId) {
  // return OwnCardService.ownAnotherCard(userId, cardId);
  // }

  // 获取指定页数的数据
  @RequestMapping(value = "/List")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public JSONObject ListPage(@RequestBody ListRequest ListRequest) {
    ListRequest.setPageSize(general_page_size);
    String request = JSON.toJSONString(ListRequest);
    System.out.print(request);
    JSONObject response = OwnCardService.ListPage(ListRequest);
    return response;
  }

  // 获取所有用户拥有卡牌关系
  @RequestMapping(value = "/getAllOwnCards")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<OwnCard> getAllOwnCards() {
    return OwnCardService.getAllOwnCards();
  }

  // 获取某一用户的所有拥有卡牌关系
  @RequestMapping(value = "/getAllOwnCardsByUserName")
  @PreAuthorize("hasRole('ROLE_ADMIN') OR #userName == authentication.name")
  public List<OwnCard> getAllOwnCardsByUserName(@RequestParam("userName") String userName) {
    Integer userId = UserService.getOneUserByUserName(userName).getUserId();
    return OwnCardService.getAllOwnCardsByUserId(userId);
  }

  // 删除一些用户拥有卡牌关系
  @RequestMapping(value = "/deleteOwnCards")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteOwnCards(@RequestParam("ownCardIds") List<Integer> ownCardIds) {
    return OwnCardService.deleteOwnCards(ownCardIds);
  }

  // 删除所有用户拥有卡牌关系
  @RequestMapping(value = "/deleteAllOwnCards")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteAll() {
    return OwnCardService.deleteAll();
  }

  // 删除某个用户拥有卡牌关系
  @RequestMapping(value = "/deleteOwnCard")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<OwnCard> deleteOwnCard(@RequestParam("userId") Integer userId, @RequestParam("cardId") Integer cardId) {
    return OwnCardService.deleteOwnCard(userId, cardId);
  }

  @RequestMapping(value = "/redistributeUpgrades")
  public @ResponseBody OwnCard redistributeUpgrades(@RequestBody OwnCard updateOwnCard) {
    return OwnCardService.redistributeUpgrades(updateOwnCard);
  }
}
