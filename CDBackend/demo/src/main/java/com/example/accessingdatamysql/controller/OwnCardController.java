package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
// import java.sql.Timestamp;
import java.util.*;

import com.example.accessingdatamysql.service.OwnCardService;

@CrossOrigin(origins = "*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/ownCard") // This means URL's start with /demo (after Application path)
public class OwnCardController {
  @Autowired
  private OwnCardService OwnCardService;

  // 获取一张用户拥有卡牌关系
  @RequestMapping(value = "/getOwnCard")
  public OwnCard findOwnCardByOwnCardId(@RequestParam("ownCardId") Integer ownCardId) {
    return OwnCardService.getOneOwnCard(ownCardId);
  }

  // 增加一个用户拥有卡牌关系
  @RequestMapping(value = "/addOwnCard")
  public @ResponseBody OwnCard addNewOwnCard(@RequestParam("userId") Integer userId,
      @RequestParam("cardId") Integer cardId) {
    return OwnCardService.addNewOwnCard(userId, cardId);
  }

  // 更新一个用户拥有卡牌关系
  @RequestMapping(value = "/updateOwnCard")
  public @ResponseBody OwnCard updateOwnCard(@RequestParam("userId") Integer userId,
      @RequestParam("cardId") Integer cardId, @RequestParam("cardLevel") Integer cardLevel,
      @RequestParam("cardCurExp") Integer cardCurExp, @RequestParam("cardLevelLimit") Integer cardLevelLimit,
      @RequestParam("repetitiveOwns") Integer repetitiveOwns, @RequestParam("accquireDate") Timestamp accquireDate) {
    return OwnCardService.updateOwnCard(userId, cardId, cardLevel, cardCurExp, cardLevelLimit, repetitiveOwns,
        accquireDate);
  }

  // 用户拥有的卡片升级
  @RequestMapping(value = "/cardLevelUp")
  public @ResponseBody OwnCard cardLevelUp(@RequestParam("userId") Integer userId,
      @RequestParam("cardId") Integer cardId) {
    return OwnCardService.cardLevelUp(userId, cardId);
  }

  // 用户再一次拥有已拥有的卡片
  // @RequestMapping(value = "/ownAnotherCard")
  // public @ResponseBody OwnCard ownAnotherCard(@RequestParam("userId") Integer
  // userId,
  // @RequestParam("cardId") Integer cardId) {
  // return OwnCardService.ownAnotherCard(userId, cardId);
  // }

  // 获取所有用户拥有卡牌关系
  @RequestMapping(value = "/getAllOwnCards")
  public List<OwnCard> getAllOwnCards() {
    return OwnCardService.getAllOwnCards();
  }

  // 获取某一用户的所有拥有卡牌关系
  @RequestMapping(value = "/getAllOwnCardsByUserId")
  public List<OwnCard> getAllOwnCardsByUserId(@RequestParam("userId") Integer userId) {
    return OwnCardService.getAllOwnCardsByUserId(userId);
  }

  // 删除一些用户拥有卡牌关系
  @RequestMapping(value = "/deleteOwnCards")
  public String deleteOwnCards(@RequestParam("ownCardIds") List<Integer> ownCardIds) {
    return OwnCardService.deleteOwnCards(ownCardIds);
  }

  // 删除所有用户拥有卡牌关系
  @RequestMapping(value = "/deleteAllOwnCards")
  public String deleteAll() {
    return OwnCardService.deleteAll();
  }

  // 删除某个用户拥有卡牌关系
  @RequestMapping(value = "/deleteOwnCard")
  public List<OwnCard> deleteOwnCard(@RequestParam("userId") Integer userId, @RequestParam("cardId") Integer cardId) {
    return OwnCardService.deleteOwnCard(userId, cardId);
  }
}
