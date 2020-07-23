package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao AdminDao;

    @Override
    public Admin getOneAdmin(Integer AdminId) {
        return AdminDao.getOneAdmin(AdminId);
    }

    public Admin addNewAdmin(Admin registerAdmin) {
        return AdminDao.addNewAdmin(registerAdmin);
    }

    public Admin updateAdmin(Admin updateAdmin) {
        return AdminDao.updateAdmin(updateAdmin);
    }

    public List<Admin> getAllAdmins() {
        return AdminDao.getAllAdmins();
    }

    public String deleteAdmins(List<Integer> AdminIds) {
        return AdminDao.deleteAdmins(AdminIds);
    }

    public String deleteAll() {
        return AdminDao.deleteAll();
    }

    public boolean identifyAdmin(String adminName, String password) {
        Admin fetchAdmin = AdminDao.getOneAdminByAdminName(adminName);
        System.out.println();
        System.out.println(fetchAdmin.getPassword());
        return password.equals(fetchAdmin.getPassword());
    }

    public List<Admin> deleteAdmin(Integer adminId) {
        return AdminDao.deleteAdmin(adminId);
    }

    public List<String> getAllAdminNames() {
        return AdminDao.getAllAdminNames();
    };

    public Integer getAdminRole(String adminName) {
        return AdminDao.getAdminRole(adminName);
    }
}
