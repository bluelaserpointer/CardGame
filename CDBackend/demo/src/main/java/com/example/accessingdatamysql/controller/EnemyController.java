package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

// import java.sql.Timestamp;
import java.util.*;

// import javax.validation.constraints.Null;

import com.example.accessingdatamysql.service.EnemyService;

@CrossOrigin(origins = "*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/Enemy") // This means URL's start with /demo (after Application path)
public class EnemyController {
  @Autowired
  private EnemyService EnemyService;

  @RequestMapping(value = "/getEnemy")
  public Enemy findEnemyByEnemyId(@RequestParam("EnemyId") Integer EnemyId) {
    return EnemyService.getOneEnemy(EnemyId);
  }

  @RequestMapping(value = "/addEnemy")
  public @ResponseBody Enemy addNewEnemy(@RequestParam("EnemyName") String EnemyName,
      @RequestParam("healthPoint") Integer healthPoint, @RequestParam("attack") Integer attack,
      @RequestParam("defense") Integer defense, @RequestParam("attackRange") Integer attackRange,
      @RequestParam("cd") Double cd, @RequestParam("speed") Integer speed, @RequestParam("EnemyImg") String EnemyImg,
      @RequestParam("shortDescription") String shortDescription,
      @RequestParam("EnemyDescription") String EnemyDescription) {
    return EnemyService.addNewEnemy(EnemyName, healthPoint, attack, defense, attackRange, cd, speed, EnemyImg,
        shortDescription, EnemyDescription);
  }

  @RequestMapping(value = "/updateEnemy")
  public @ResponseBody Enemy updateEnemy(@RequestParam("EnemyId") Integer EnemyId,
      @RequestParam("EnemyName") String EnemyName, @RequestParam("healthPoint") Integer healthPoint,
      @RequestParam("attack") Integer attack, @RequestParam("defense") Integer defense,
      @RequestParam("attackRange") Integer attackRange, @RequestParam("cd") Double cd,
      @RequestParam("speed") Integer speed, @RequestParam("EnemyImg") String EnemyImg,
      @RequestParam("shortDescription") String shortDescription,
      @RequestParam("EnemyDescription") String EnemyDescription) {
    System.out.println("In controller");

    return EnemyService.updateEnemy(EnemyId, EnemyName, healthPoint, attack, defense, attackRange, cd, speed, EnemyImg,
        shortDescription, EnemyDescription);
  }

  @RequestMapping(value = "/getAllEnemies")
  public List<Enemy> getAllEnemies() {
    return EnemyService.getAllEnemies();
  }

  @RequestMapping(value = "/deleteEnemys")
  public String deleteEnemies(@RequestParam("EnemyIds") List<Integer> EnemyIds) {
    return EnemyService.deleteEnemies(EnemyIds);
  }

  @RequestMapping(value = "/deleteAllEnemies")
  public String deleteAll() {
    return EnemyService.deleteAll();
  }

  @RequestMapping(value = "/deleteEnemy")
  public List<Enemy> deleteEnemy(@RequestParam("EnemyId") Integer EnemyId) {
    return EnemyService.deleteEnemy(EnemyId);
  }

}
