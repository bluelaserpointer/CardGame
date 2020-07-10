package com.example.accessingdatamysql.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;
// import java.util.Optional;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findAdminByAdminNameEquals(String adminName);

    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE Admin u SET u.storage = :newstorage WHERE u.AdminId =
    // :AdminId")
    // int updateStorageStatus(@Param("newstorage") Integer newstorage,
    // @Param("AdminId") Integer AdminId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Admin u SET u = :newAdmin WHERE u.adminId = :AdminId")
    int updateAdminStatus(@Param("newAdmin") Admin newAdmin, @Param("AdminId") Integer AdminId);

}
