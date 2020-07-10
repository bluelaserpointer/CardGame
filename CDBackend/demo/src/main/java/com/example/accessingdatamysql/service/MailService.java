package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.*;

import java.util.List;

public interface MailService {
        Mail getOneMail(Integer MailId);

        String addNewMail(String MailName, String mailImg, String mailDescription);

        String updateMail(Integer MailId, String MailName, String mailImg, String mailDescription);

        List<Mail> getAllMails();

        String deleteMails(List<Integer> MailIds);

        String deleteAll();

        List<Mail> deleteMail(Integer mailId);
}
