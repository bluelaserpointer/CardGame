package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Activity;
import com.example.accessingdatamysql.entity.Admin;

import java.util.List;

public interface AdminService {
        Admin getOneAdmin(Integer AdminId);

        Admin addNewAdmin(Admin registerAdmin);

        Admin updateAdmin(Admin updateAdmin);

        List<Admin> getAllAdmins();

        String deleteAdmins(List<Integer> AdminIds);

        String deleteAll();

        boolean identifyAdmin(String adminName, String password);

        List<Admin> deleteAdmin(Integer adminId);

        List<String> getAllAdminNames();

        Integer getAdminRole(String adminName);
}
