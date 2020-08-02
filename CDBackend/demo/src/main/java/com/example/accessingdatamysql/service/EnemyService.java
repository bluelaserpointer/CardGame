package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Enemy;
import com.example.accessingdatamysql.entity.ListRequest;

import java.util.List;

public interface EnemyService {
        Enemy getOneEnemy(Integer EnemyId);

        Enemy addNewEnemy(Enemy newEnemy);

        Enemy updateEnemy(Enemy updateEnemy);

        // 获取指定页数的Enemy
        List<Enemy> ListPage(ListRequest listRequest);

        List<Enemy> getAllEnemies();

        String deleteEnemies(List<Integer> EnemyIds);

        String deleteAll();

        List<Enemy> deleteEnemy(Integer enemyId);
}
