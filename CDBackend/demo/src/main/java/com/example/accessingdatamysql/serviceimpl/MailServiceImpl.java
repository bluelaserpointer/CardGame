package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private MailDao MailDao;

    @Override
    public Mail getOneMail(Integer MailId) {
        return MailDao.getOneMail(MailId);
    }

    public String addNewMail(String MailName, String mailImg, String mailDescription) {
        return MailDao.addNewMail(MailName, mailImg, mailDescription);
    }

    public String updateMail(Integer MailId, String MailName, String mailImg, String mailDescription) {
        return MailDao.updateMail(MailId, MailName, mailImg, mailDescription);
    }

    public List<Mail> getAllMails() {
        return MailDao.getAllMails();
    }

    public String deleteMails(List<Integer> MailIds) {
        return MailDao.deleteMails(MailIds);
    }

    public String deleteAll() {
        return MailDao.deleteAll();
    }

    public List<Mail> deleteMail(Integer mailId) {
        return MailDao.deleteMail(mailId);
    }
}
