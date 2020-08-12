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
import static com.example.accessingdatamysql.GlobalConstants.general_page_size;

// import javax.validation.constraints.Null;

import com.example.accessingdatamysql.service.EnemyService;

@CrossOrigin(origins = "*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/enemy") // This means URL's start with /demo (after Application path)
public class EnemyController {
  @Autowired
  private EnemyService EnemyService;

  @RequestMapping(value = "/getEnemy")
  public Enemy findEnemyByEnemyId(@RequestParam("enemyId") Integer EnemyId) {
    return EnemyService.getOneEnemy(EnemyId);
  }

  @RequestMapping(value = "/addEnemy")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody Enemy addNewEnemy(@RequestBody Enemy newEnemy) {
    // System.out.println("Class: EnemyController Method: addNewEnemy Param: ");

    return EnemyService.addNewEnemy(newEnemy);
  }

  @RequestMapping(value = "/updateEnemy")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody Enemy updateEnemy(@RequestBody Enemy updateEnemy) {
    return EnemyService.updateEnemy(updateEnemy);
  }

  // 获取指定页数的数据
  @RequestMapping(value = "/List")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public JSONObject ListPage(@RequestBody ListRequest ListRequest) {
    ListRequest.setPageSize(general_page_size);
    final String request = JSON.toJSONString(ListRequest);
    System.out.print(request);
    return EnemyService.ListPage(ListRequest);
  }

  @RequestMapping(value = "/getAllEnemies")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<Enemy> getAllEnemies() {
    return EnemyService.getAllEnemies();
  }

  @RequestMapping(value = "/deleteEnemies")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteEnemies(@RequestParam("enemyIds") List<Integer> EnemyIds) {
    return EnemyService.deleteEnemies(EnemyIds);
  }

  @RequestMapping(value = "/deleteAllEnemies")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteAllEnemies() {
    return EnemyService.deleteAll();
  }

  @RequestMapping(value = "/deleteEnemy")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<Enemy> deleteEnemy(@RequestParam("enemyId") Integer EnemyId) {
    return EnemyService.deleteEnemy(EnemyId);
  }

}
