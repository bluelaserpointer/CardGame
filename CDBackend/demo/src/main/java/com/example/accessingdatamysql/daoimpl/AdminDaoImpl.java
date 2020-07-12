package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.AdminDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.io.Console;
import java.util.*;

@Repository
public class AdminDaoImpl implements AdminDao {
    @Autowired
    private AdminRepository AdminRepository;

    @Override
    public Admin getOneAdmin(Integer AdminId) {
        return AdminRepository.getOne(AdminId);
    }

    public String addNewAdmin(String adminName, String password) {
        Admin Admin = new Admin(adminName, password);
        // System.out.println("new Admin has an Id of : " + n.getAdminId());
        AdminRepository.save(Admin);
        return "Saved Admin";
    }

    public String updateAdmin(Integer AdminId, String adminName, String password) {

        Admin Admin = AdminRepository.getOne(AdminId);
        // System.out.println("old Admin has an Id of : " + n.getAdminId());
        Admin.setAdmin(adminName, password);

        AdminRepository.updateAdminStatus(Admin, AdminId);
        // return "Modified Admin";
        return "modified Admin: " + Admin.getAdminName();

    }

    public List<Admin> getAllAdmins() {
        List<Admin> Admins = AdminRepository.findAll();
        return Admins;
    }

    public String deleteAdmins(List<Integer> AdminIds) {
        for (int i = 0; i < AdminIds.size(); i++) {
            AdminRepository.deleteById(AdminIds.get(i));
        }
        return "Deleted Admins by id";
    }

    public String deleteAll() {
        AdminRepository.deleteAll();
        return "Deleted All Admins";
    }

    public Admin getOneAdminByAdminName(String adminName){
        System.out.println(adminName);
        System.out.println("In AdminDao");
        return AdminRepository.findAdminByAdminNameEquals(adminName);
    }

    public List<Admin> deleteAdmin(Integer adminId) {
        AdminRepository.deleteById(adminId);
        return getAllAdmins();
    }

}
