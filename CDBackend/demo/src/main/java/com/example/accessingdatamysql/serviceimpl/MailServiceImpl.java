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

    public Mail addNewMail(Mail newMail) {
        return MailDao.addNewMail(newMail);
    }

    public Mail updateMail(Mail updateMail) {
        return MailDao.updateMail(updateMail);
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
