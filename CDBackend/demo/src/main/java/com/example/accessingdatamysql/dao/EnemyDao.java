package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.*;

public interface EnemyDao {

        Enemy getOneEnemy(Integer enemyId);

        Enemy addNewEnemy(Enemy newEnemy);

        Enemy updateEnemy(Enemy updateEnemy);

        JSONObject ListPage(Integer page_token, Integer page_size);

        List<Enemy> getAllEnemies();

        String deleteEnemies(List<Integer> enemyIds);

        String deleteAll();

        List<Enemy> deleteEnemy(Integer EnemyId);

}
