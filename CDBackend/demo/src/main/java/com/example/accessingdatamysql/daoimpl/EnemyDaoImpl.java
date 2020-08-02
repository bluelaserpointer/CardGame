package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.EnemyDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.io.Console;
import java.util.*;

@Repository
public class EnemyDaoImpl implements EnemyDao {
    @Autowired
    private EnemyRepository EnemyRepository;
    @Autowired
    private EnemyDetailsRepository EnemyDetailsRepository;

    @Override
    public Enemy getOneEnemy(Integer enemyId) {
        Enemy Enemy = EnemyRepository.getOne(enemyId);
        Optional<EnemyDetails> EnemyDetails = EnemyDetailsRepository.findEnemyDetailsByEnemyIdEquals(enemyId);
        EnemyDetails.ifPresent(Enemy::setEnemyDetails);
        return Enemy;
    }

    @Override
    public Enemy addNewEnemy(Enemy newEnemy) {
        Enemy enemy = new Enemy(newEnemy.getEnemyName(), newEnemy.getHealthPoint(), newEnemy.getAttack(),
                newEnemy.getDefense(), newEnemy.getAttackRange(), newEnemy.getCd(), newEnemy.getSpeed());
        EnemyRepository.save(enemy);
        System.out.println(enemy.getEnemyName());

        EnemyDetails enemyDetails = new EnemyDetails(enemy.getEnemyId(), newEnemy.getEnemyDetails().getEnemyImg(),
                newEnemy.getEnemyDetails().getShortDescription(), newEnemy.getEnemyDetails().getEnemyDescription());
        EnemyDetailsRepository.save(enemyDetails);
        enemy.setEnemyDetails(enemyDetails);
        System.out.println(enemyDetails.getEnemyDescription());
        return enemy;
    }

    public Enemy updateEnemy(Enemy updateEnemy) {
        Enemy Enemy = EnemyRepository.getOne(updateEnemy.getEnemyId());
        Enemy.setEnemy(updateEnemy.getEnemyName(), updateEnemy.getHealthPoint(), updateEnemy.getAttack(),
                updateEnemy.getDefense(), updateEnemy.getAttackRange(), updateEnemy.getCd(), updateEnemy.getSpeed());

        EnemyRepository.updateEnemyStatus(Enemy, updateEnemy.getEnemyId());
        Optional<EnemyDetails> optEnemyDetails = EnemyDetailsRepository
                .findEnemyDetailsByEnemyIdEquals(Enemy.getEnemyId());
        EnemyDetails EnemyDetails = new EnemyDetails(Enemy.getEnemyId(), "", "", "");
        if (optEnemyDetails.isPresent()) {
            System.out.println("Enemy Exists");
            EnemyDetails = optEnemyDetails.get();
        } else {
            System.out.println("Enemy doesn't exist");
        }

        EnemyDetails.setEnemyDescription(Enemy.getEnemyDetails().getEnemyDescription());
        EnemyDetails.setShortDescription(Enemy.getEnemyDetails().getShortDescription());
        EnemyDetails.setEnemyImg(Enemy.getEnemyDetails().getEnemyImg());
        EnemyDetailsRepository.save(EnemyDetails);
        Enemy.setEnemyDetails(EnemyDetails);

        return Enemy;
    }

    public List<Enemy> getAllEnemies() {
        List<Enemy> Enemys = EnemyRepository.findAll();
        for (int i = 0; i < Enemys.size(); i++) {
            Enemy Enemy = Enemys.get(i);
            Optional<EnemyDetails> EnemyDetails = EnemyDetailsRepository
                    .findEnemyDetailsByEnemyIdEquals(Enemy.getEnemyId());
            EnemyDetails.ifPresent(Enemy::setEnemyDetails);
            Enemys.set(i, Enemy);
        }
        return Enemys;
    }

    public String deleteEnemies(List<Integer> EnemyIds) {
        for (int i = 0; i < EnemyIds.size(); i++) {
            EnemyRepository.deleteById(EnemyIds.get(i));
            EnemyDetailsRepository.deleteEnemyDetailsByEnemyIdEquals(EnemyIds.get(i));
        }
        return "Deleted Enemys by id";
    }

    public String deleteAll() {
        EnemyRepository.deleteAll();
        EnemyDetailsRepository.deleteAll();
        return "Deleted All Enemys";
    }

    public List<Enemy> deleteEnemy(Integer EnemyId) {
        EnemyRepository.deleteById(EnemyId);
        EnemyDetailsRepository.deleteEnemyDetailsByEnemyIdEquals(EnemyId);
        return getAllEnemies();
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        JSONObject response = new JSONObject();

        // get the result data
        Integer start = (page_token - 1) * page_size + 1;
        Integer end = page_token * page_size;
        List<Enemy> enemies = EnemyRepository.ListPage(start, end);
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            Optional<EnemyDetails> enemyDetails = EnemyDetailsRepository
                    .findEnemyDetailsByEnemyIdEquals(enemy.getEnemyId());
            enemyDetails.ifPresent(enemy::setEnemyDetails);
            enemies.set(i, enemy);
        }
        // get the nextPageToken
        Integer nextPageToken;
        if ((EnemyRepository.findAll().size() - (page_token * page_size)) <= 0) {
            response.put("nextPageToken", "");
        } else {
            nextPageToken = page_token + 1;
            response.put("nextPageToken", nextPageToken);
        }

        // get the total pages of the result
        Integer totalPages = EnemyRepository.findAll().size() / page_size;
        totalPages = totalPages + 1;

        response.put("result", enemies);
        response.put("totalPages", totalPages);

        return response;
    }

}
