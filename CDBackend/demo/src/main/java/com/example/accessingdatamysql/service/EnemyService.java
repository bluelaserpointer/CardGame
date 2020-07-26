package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Enemy;

import java.util.List;

public interface EnemyService {
        Enemy getOneEnemy(Integer EnemyId);

        Enemy addNewEnemy(Enemy newEnemy);

        Enemy updateEnemy(Enemy updateEnemy);

        List<Enemy> getAllEnemies();

        String deleteEnemies(List<Integer> EnemyIds);

        String deleteAll();

        List<Enemy> deleteEnemy(Integer enemyId);
}
