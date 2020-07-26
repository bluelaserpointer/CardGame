package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

// import java.sql.Timestamp;
import java.util.*;

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
  public @ResponseBody Enemy addNewEnemy(@RequestParam("enemyName") String enemyName,
      @RequestParam("healthPoint") Integer healthPoint, @RequestParam("attack") Integer attack,
      @RequestParam("defense") Integer defense, @RequestParam("attackRange") Integer attackRange,
      @RequestParam("cd") Double cd, @RequestParam("speed") Integer speed, @RequestParam("enemyImg") String enemyImg,
      @RequestParam("shortDescription") String shortDescription,
      @RequestParam("enemyDescription") String enemyDescription) {
    System.out.println("Class: EnemyController Method: addNewEnemy Param: ");

    return EnemyService.addNewEnemy(enemyName, healthPoint, attack, defense, attackRange, cd, speed, enemyImg,
        shortDescription, enemyDescription);
  }

  @RequestMapping(value = "/updateEnemy")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody Enemy updateEnemy(@RequestParam("enemyId") Integer EnemyId,
      @RequestParam("enemyName") String EnemyName, @RequestParam("healthPoint") Integer healthPoint,
      @RequestParam("attack") Integer attack, @RequestParam("defense") Integer defense,
      @RequestParam("attackRange") Integer attackRange, @RequestParam("cd") Double cd,
      @RequestParam("speed") Integer speed, @RequestParam("enemyImg") String EnemyImg,
      @RequestParam("shortDescription") String shortDescription,
      @RequestParam("enemyDescription") String EnemyDescription) {
    System.out.println("In controller");

    return EnemyService.updateEnemy(EnemyId, EnemyName, healthPoint, attack, defense, attackRange, cd, speed, EnemyImg,
        shortDescription, EnemyDescription);
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
