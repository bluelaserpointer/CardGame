package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface AdminDao {
        Admin getOneAdmin(Integer AdminId);

        Admin addNewAdmin(Admin registerAdmin);

        Admin updateAdmin(Admin updateAdmin);

        List<Admin> getAllAdmins();

        String deleteAdmins(List<Integer> AdminIds);

        String deleteAll();

        Admin getOneAdminByAdminName(String userName);

        List<Admin> deleteAdmin(Integer adminId);

        List<String> getAllAdminNames();

        Integer getAdminRole(String adminName);

}
