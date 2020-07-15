package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Enemy;

import java.util.List;

public interface EnemyService {
        Enemy getOneEnemy(Integer EnemyId);

        Enemy addNewEnemy(String enemyName, Integer healthPoint, Integer attack, Integer defense, Integer attackRange,
                        Double cd, Integer speed, String enemyImg, String shortDescription, String enemyDescription);

        Enemy updateEnemy(Integer enemyId, String enemyName, Integer healthPoint, Integer attack, Integer defense,
                        Integer attackRange, Double cd, Integer speed, String enemyImg, String shortDescription,
                        String enemyDescription);

        List<Enemy> getAllEnemies();

        String deleteEnemies(List<Integer> EnemyIds);

        String deleteAll();

        List<Enemy> deleteEnemy(Integer enemyId);
}
