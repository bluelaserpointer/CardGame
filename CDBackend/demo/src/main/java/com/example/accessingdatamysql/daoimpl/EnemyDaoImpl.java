package com.example.accessingdatamysql.daoimpl;

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
    public Enemy addNewEnemy(String enemyName, Integer healthPoint, Integer attack, Integer defense,
            Integer attackRange, Double cd, Integer speed, String enemyImg, String shortDescription,
            String enemyDescription) {
        Enemy enemy = new Enemy(enemyName, healthPoint, attack, defense, attackRange, cd, speed);
        EnemyRepository.save(enemy);
        EnemyDetails enemyDetails = new EnemyDetails(enemy.getEnemyId(), enemyImg, shortDescription, enemyDescription);
        EnemyDetailsRepository.save(enemyDetails);
        enemy.setEnemyDetails(enemyDetails);
        return enemy;
    }

    public Enemy updateEnemy(Integer enemyId, String enemyName, Integer healthPoint, Integer attack, Integer defense,
            Integer attackRange, Double cd, Integer speed, String enemyImg, String shortDescription,
            String enemyDescription) {
        Enemy Enemy = EnemyRepository.getOne(enemyId);
        Enemy.setEnemy(enemyName, healthPoint, attack, defense, attackRange, cd, speed);

        EnemyRepository.updateEnemyStatus(Enemy, enemyId);
        Optional<EnemyDetails> optEnemyDetails = EnemyDetailsRepository.findEnemyDetailsByEnemyIdEquals(enemyId);
        EnemyDetails EnemyDetails = new EnemyDetails(enemyId, "", "", "");
        if (optEnemyDetails.isPresent()) {
            System.out.println("Enemy Exists");
            EnemyDetails = optEnemyDetails.get();
        } else {
            System.out.println("Enemy doesn't exist");
        }

        EnemyDetails.setEnemyDescription(enemyDescription);
        EnemyDetails.setShortDescription(shortDescription);
        EnemyDetails.setEnemyImg(enemyImg);
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

}
