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
    private EnemyRepository enemyRepository;
    @Autowired
    private EnemyDetailsRepository enemyDetailsRepository;

    @Override
    public Enemy getOneEnemy(Integer enemyId) {
        Enemy Enemy = enemyRepository.getOne(enemyId);
        Optional<EnemyDetails> EnemyDetails = enemyDetailsRepository.findEnemyDetailsByEnemyIdEquals(enemyId);
        EnemyDetails.ifPresent(Enemy::setEnemyDetails);
        return Enemy;
    }

    @Override
    public Enemy addNewEnemy(Enemy newEnemy) {
        Enemy enemy = new Enemy(newEnemy.getEnemyName(), newEnemy.getHealthPoint(), newEnemy.getAttack(),
                newEnemy.getDefense(), newEnemy.getAttackRange(), newEnemy.getCd(), newEnemy.getSpeed());
        enemyRepository.save(enemy);
        System.out.println(enemy.getEnemyName());

        EnemyDetails enemyDetails = new EnemyDetails(enemy.getEnemyId(), newEnemy.getEnemyDetails().getEnemyImg(),
                newEnemy.getEnemyDetails().getShortDescription(), newEnemy.getEnemyDetails().getEnemyDescription());
        enemyDetailsRepository.save(enemyDetails);
        enemy.setEnemyDetails(enemyDetails);
        System.out.println(enemyDetails.getEnemyDescription());
        return enemy;
    }

    public Enemy updateEnemy(Enemy updateEnemy) {
        Enemy Enemy = enemyRepository.getOne(updateEnemy.getEnemyId());
        Enemy.setEnemy(updateEnemy.getEnemyName(), updateEnemy.getHealthPoint(), updateEnemy.getAttack(),
                updateEnemy.getDefense(), updateEnemy.getAttackRange(), updateEnemy.getCd(), updateEnemy.getSpeed());

        enemyRepository.updateEnemyStatus(Enemy, updateEnemy.getEnemyId());
        Optional<EnemyDetails> optEnemyDetails = enemyDetailsRepository
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
        enemyDetailsRepository.save(EnemyDetails);
        Enemy.setEnemyDetails(EnemyDetails);

        return Enemy;
    }

    public List<Enemy> getAllEnemies() {
        List<Enemy> Enemys = enemyRepository.findAll();
        for (int i = 0; i < Enemys.size(); i++) {
            Enemy Enemy = Enemys.get(i);
            Optional<EnemyDetails> EnemyDetails = enemyDetailsRepository
                    .findEnemyDetailsByEnemyIdEquals(Enemy.getEnemyId());
            EnemyDetails.ifPresent(Enemy::setEnemyDetails);
            Enemys.set(i, Enemy);
        }
        return Enemys;
    }

    public String deleteEnemies(List<Integer> EnemyIds) {
        for (int i = 0; i < EnemyIds.size(); i++) {
            enemyRepository.deleteById(EnemyIds.get(i));
            enemyDetailsRepository.deleteEnemyDetailsByEnemyIdEquals(EnemyIds.get(i));
        }
        return "Deleted Enemys by id";
    }

    public String deleteAll() {
        enemyRepository.deleteAll();
        enemyDetailsRepository.deleteAll();
        return "Deleted All Enemys";
    }

    public List<Enemy> deleteEnemy(Integer EnemyId) {
        enemyRepository.deleteById(EnemyId);
        enemyDetailsRepository.deleteEnemyDetailsByEnemyIdEquals(EnemyId);
        return getAllEnemies();
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        return this.ListPage(page_token, page_size, enemyRepository, enemy -> {
            enemyDetailsRepository
                    .findEnemyDetailsByEnemyIdEquals(enemy.getEnemyId())
                    .ifPresent(enemy::setEnemyDetails);
            return enemy;
        });
    }

}
