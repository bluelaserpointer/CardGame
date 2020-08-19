package com.example.accessingdatamysql.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.accessingdatamysql.Classes.PaginationJpaRepository;
import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MailRepository extends PaginationJpaRepository<Mail, Integer> {

    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE Mail u SET u.storage = :newstorage WHERE u.MailId =
    // :MailId")
    // int updateStorageStatus(@Param("newstorage") Integer newstorage,
    // @Param("MailId") Integer MailId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Mail u SET u = :newMail WHERE u.mailId = :MailId")
    void updateMailStatus(@Param("newMail") Mail newMail, @Param("MailId") Integer MailId);

    @Transactional
    @Query(value = "SELECT * from mail LIMIT ?1,?2", nativeQuery = true)
    List<Mail> ListPage(Integer start, Integer end);

}
