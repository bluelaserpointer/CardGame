package com.example.accessingdatamysql.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

// import java.sql.Timestamp;
import java.util.*;

// import javax.validation.constraints.Null;

import com.example.accessingdatamysql.service.OwnItemService;
import com.example.accessingdatamysql.service.UserService;

import static com.example.accessingdatamysql.GlobalConstants.general_page_size;

@CrossOrigin(origins = "*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/ownItem") // This means URL's start with /demo (after Application path)
public class OwnItemController {
  @Autowired // This means to get the bean called OwnItemRepository
  // Which is auto-generated by Spring, we will use it to handle the data
  private OwnItemService OwnItemService;

  @Autowired
  private UserService UserService;

  @RequestMapping(value = "/getOwnItem")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public OwnItem findOwnItemByOwnItemId(@RequestParam("ownItemId") Integer ownItemId) {
    return OwnItemService.getOneOwnItem(ownItemId);
  }

  @RequestMapping(value = "/addOwnItem")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody OwnItem addNewOwnItem(@RequestBody OwnItem newOwnItem) {
    // JSONObject jsonObject = JSON.parseObject(newOwnItem);
    // OwnItem ownItem = JSON.parseObject(jsonObject.getString("OwnItem"),
    // OwnItem.class);
    return OwnItemService.addNewOwnItem(newOwnItem);
  }

  @RequestMapping(value = "/updateOwnItem")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody OwnItem updateOwnItem(@RequestBody OwnItem updateOwnItem) {
    // JSONObject jsonObject = JSON.parseObject(updateOwnItem);
    // return
    // OwnItemService.updateOwnItem(JSON.parseObject(jsonObject.getString("OwnItem"),
    // OwnItem.class));
    return OwnItemService.updateOwnItem(updateOwnItem);
  }

  // 获取指定页数的数据
  @RequestMapping(value = "/List")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public JSONObject ListPage(@RequestBody ListRequest ListRequest) {
    ListRequest.setPageSize(general_page_size);
    final String request = JSON.toJSONString(ListRequest);
    System.out.print(request);
    JSONObject response = OwnItemService.ListPage(ListRequest);
    return response;
  }

  @RequestMapping(value = "/getAllOwnItems")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<OwnItem> getAllOwnItems() {
    return OwnItemService.getAllOwnItems();
  }

  @RequestMapping(value = "/deleteOwnItems")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteOwnItems(@RequestParam("ownItemIds") List<Integer> ownItemIds) {
    return OwnItemService.deleteOwnItems(ownItemIds);
  }

  @RequestMapping(value = "/deleteAllOwnItems")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteAll() {
    return OwnItemService.deleteAll();
  }

  @RequestMapping(value = "/deleteOwnItem")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<OwnItem> deleteOwnItem(@RequestParam("userId") Integer userId, @RequestParam("itemId") Integer itemId) {
    System.out.println("In controller");
    return OwnItemService.deleteOwnItem(userId, itemId);
  }

  // 获取某一用户的所有拥有道具关系
  @RequestMapping(value = "/getAllOwnItemsByUserName")
  @PreAuthorize("hasRole('ROLE_ADMIN') OR #userName == authentication.name")
  public List<OwnItem> getAllOwnItemsByUserName(@RequestParam("userName") String userName) {
    final Integer userId = UserService.getOneUserByUserName(userName).getUserId();
    return OwnItemService.getAllOwnItemsByUserId(userId);
  }
}
