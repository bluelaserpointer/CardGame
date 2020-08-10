package com.example.accessingdatamysql.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;
// import java.util.Optional;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MailBoxRepository extends JpaRepository<MailBox, Integer> {

    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE MailBox u SET u.storage = :newstorage WHERE
    // u.MailBoxId =
    // :MailBoxId")
    // int updateStorageStatus(@Param("newstorage") Integer newstorage,
    // @Param("MailBoxId") Integer MailBoxId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE MailBox u SET u = :newMailBox WHERE u.mailBoxId = :MailBoxId")
    int updateMailBoxStatus(@Param("newMailBox") MailBox newMailBox, @Param("MailBoxId") Integer MailBoxId);

}
