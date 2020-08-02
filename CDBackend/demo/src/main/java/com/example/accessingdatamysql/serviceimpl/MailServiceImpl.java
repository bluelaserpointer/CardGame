package com.example.accessingdatamysql.serviceimpl;

import com.alibaba.fastjson.JSONObject;
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
    @Autowired
    private MailBoxDao MailBoxDao;

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

    @Override
    public MailBox sendMail(Integer mailId, Integer userId) {
        MailBox userMailBox = MailBoxDao.getOneMailBox(userId);
        List<Integer> mailIds = userMailBox.getMailIds();
        mailIds.add(mailId);
        MailBoxDao.updateMailBox(userId, mailIds);
        return null;
    }

    @Override
    public Mail sendMailToAllUsers(Integer mailId) {
        List<MailBox> allMailBoxes = MailBoxDao.getAllMailBoxs();
        for (int i = 0; i < allMailBoxes.size(); i++) {
            MailBox MailBox = allMailBoxes.get(i);
            List<Integer> mailIds = MailBox.getMailIds();
            mailIds.add(mailId);
            MailBoxDao.updateMailBox(MailBox.getMailBoxId(), mailIds);
        }
        return MailDao.getOneMail(mailId);
    }

    @Override
    public JSONObject ListPage(ListRequest listRequest) {
        return MailDao.ListPage(listRequest.getPageToken(), listRequest.getPageSize());
    }
}
