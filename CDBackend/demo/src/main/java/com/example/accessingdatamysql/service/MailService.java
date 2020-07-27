package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.*;

import java.util.List;

public interface MailService {
        Mail getOneMail(Integer MailId);

        Mail addNewMail(Mail newMail);

        Mail updateMail(Mail updateMail);

        List<Mail> getAllMails();

        String deleteMails(List<Integer> MailIds);

        String deleteAll();

        List<Mail> deleteMail(Integer mailId);

        MailBox sendMail(Integer mailId, Integer userId);

        Mail sendMailToAllUsers(Integer mailId);
}
