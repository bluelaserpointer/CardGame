package com.example.accessingdatamysql.serviceimpl;

import com.alibaba.fastjson.JSONObject;
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
    public Enemy addNewEnemy(Enemy newEnemy) {
        return EnemyDao.addNewEnemy(newEnemy);
    }

    @Override
    public Enemy updateEnemy(Enemy updateEnemy) {
        return EnemyDao.updateEnemy(updateEnemy);
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

    @Override
    public JSONObject ListPage(ListRequest listRequest) {
        return EnemyDao.ListPage(listRequest.getPageToken(), listRequest.getPageSize());
    }

}
