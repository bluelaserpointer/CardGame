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

    public Admin addNewAdmin(Admin registerAdmin) {
        Admin Admin = new Admin(registerAdmin.getAdminName(), registerAdmin.getPassword(), registerAdmin.getRole());
        // System.out.println("new Admin has an Id of : " + n.getAdminId());
        AdminRepository.save(Admin);
        return Admin;
    }

    public Admin updateAdmin(Admin updateAdmin) {

        Admin Admin = AdminRepository.getOne(updateAdmin.getAdminId());
        // System.out.println("old Admin has an Id of : " + n.getAdminId());
        Admin.setAdmin(updateAdmin.getAdminName(), updateAdmin.getPassword(), updateAdmin.getRole());

        AdminRepository.updateAdminStatus(Admin, updateAdmin.getAdminId());
        // return "Modified Admin";
        return Admin;

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

    public Admin getOneAdminByAdminName(String adminName) {
        System.out.println(adminName);
        System.out.println("In AdminDao");
        return AdminRepository.findAdminByAdminNameEquals(adminName);
    }

    public List<Admin> deleteAdmin(Integer adminId) {
        AdminRepository.deleteById(adminId);
        return getAllAdmins();
    }

    public List<String> getAllAdminNames() {
        List<Admin> adminList = AdminRepository.findAll();
        List<String> nameList = new LinkedList<>();
        ;
        for (int i = 0; i < adminList.size(); i++) {
            nameList.add(adminList.get(i).getAdminName());
        }
        System.out.println(nameList);
        return nameList;
    };

    public Integer getAdminRole(String adminName) {
        return AdminRepository.findAdminByAdminNameEquals(adminName).getRole();
    }
}
