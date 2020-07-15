package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface EnemyDao {
        Enemy getOneEnemy(Integer enemyId);

        Enemy addNewEnemy(String enemyName, Integer healthPoint, Integer attack, Integer defense, Integer attackRange,
                        Double cd, Integer speed, String enemyImg, String shortDescription, String enemyDescription);

        Enemy updateEnemy(Integer enemyId, String enemyName, Integer healthPoint, Integer attack, Integer defense,
                        Integer attackRange, Double cd, Integer speed, String enemyImg, String shortDescription,
                        String enemyDescription);

        List<Enemy> getAllEnemies();

        String deleteEnemies(List<Integer> enemyIds);

        String deleteAll();

        List<Enemy> deleteEnemy(Integer EnemyId);

}
