package com.example.accessingdatamysql.dao;

import java.util.List;

import com.example.accessingdatamysql.Classes.PaginationDao;
import com.example.accessingdatamysql.entity.*;

public interface EnemyDao extends PaginationDao {

        Enemy getOneEnemy(Integer enemyId);

        Enemy addNewEnemy(Enemy newEnemy);

        Enemy updateEnemy(Enemy updateEnemy);

        List<Enemy> getAllEnemies();

        String deleteEnemies(List<Integer> enemyIds);

        String deleteAll();

        List<Enemy> deleteEnemy(Integer EnemyId);

}
