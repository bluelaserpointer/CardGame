package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Activity;
import com.example.accessingdatamysql.entity.Admin;

import java.util.List;

public interface AdminService {
        Admin getOneAdmin(Integer AdminId);

        String addNewAdmin(String adminName, String password);

        String updateAdmin(Integer AdminId, String adminName, String password);

        List<Admin> getAllAdmins();

        String deleteAdmins(List<Integer> AdminIds);

        String deleteAll();

        boolean identifyAdmin(String adminName, String password);

        List<Admin> deleteAdmin(Integer adminId);
}
