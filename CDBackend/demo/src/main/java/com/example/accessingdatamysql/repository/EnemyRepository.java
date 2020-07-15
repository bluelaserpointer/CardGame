package com.example.accessingdatamysql.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;
// import java.util.Optional;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface EnemyRepository extends JpaRepository<Enemy, Integer> {

    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE Enemy u SET u.storage = :newstorage WHERE u.EnemyId =
    // :EnemyId")
    // int updateStorageStatus(@Param("newstorage") Integer newstorage,
    // @Param("EnemyId") Integer EnemyId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Enemy u SET u = :newEnemy WHERE u.enemyId = :enemyId")
    int updateEnemyStatus(@Param("newEnemy") Enemy newEnemy, @Param("enemyId") Integer enemyId);

}