package com.example.accessingdatamysql.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.accessingdatamysql.Classes.PaginationJpaRepository;
import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface EnemyRepository extends PaginationJpaRepository<Enemy, Integer> {

    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE Enemy u SET u.storage = :newstorage WHERE u.EnemyId =
    // :EnemyId")
    // int updateStorageStatus(@Param("newstorage") Integer newstorage,
    // @Param("EnemyId") Integer EnemyId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE enemy u SET u = :newEnemy WHERE u.enemyId = :enemyId")
    int updateEnemyStatus(@Param("newEnemy") Enemy newEnemy, @Param("enemyId") Integer enemyId);

    @Transactional
    @Modifying
    @Query(value = "SELECT * from enemy LIMIT ?1,?2", nativeQuery = true)
    List<Enemy> ListPage(Integer start, Integer end);

}
