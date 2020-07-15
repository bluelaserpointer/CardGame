package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.EnemyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EnemyServiceImpl implements EnemyService {
    @Autowired
    private EnemyDao EnemyDao;

    @Override
    public Enemy getOneEnemy(Integer EnemyId) {
        return EnemyDao.getOneEnemy(EnemyId);
    }

    @Override
    public Enemy addNewEnemy(String enemyName, Integer healthPoint, Integer attack, Integer defense,
            Integer attackRange, Double cd, Integer speed, String enemyImg, String shortDescription,
            String enemyDescription) {
        return EnemyDao.addNewEnemy(enemyName, healthPoint, attack, defense, attackRange, cd, speed, enemyImg,
                shortDescription, enemyDescription);
    }

    @Override
    public Enemy updateEnemy(Integer enemyId, String enemyName, Integer healthPoint, Integer attack, Integer defense,
            Integer attackRange, Double cd, Integer speed, String enemyImg, String shortDescription,
            String enemyDescription) {
        return EnemyDao.updateEnemy(enemyId, enemyName, healthPoint, attack, defense, attackRange, cd, speed, enemyImg,
                shortDescription, enemyDescription);
    }

    @Override
    public List<Enemy> getAllEnemies() {
        return EnemyDao.getAllEnemies();
    }

    @Override
    public String deleteEnemies(List<Integer> enemyIds) {
        return EnemyDao.deleteEnemies(enemyIds);
    }

    @Override
    public String deleteAll() {
        return EnemyDao.deleteAll();
    }

    public List<Enemy> deleteEnemy(Integer enemyId) {
        return EnemyDao.deleteEnemy(enemyId);
    }

}
