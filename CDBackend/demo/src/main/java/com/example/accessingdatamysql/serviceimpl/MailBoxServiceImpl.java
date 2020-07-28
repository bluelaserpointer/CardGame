package com.example.accessingdatamysql.serviceimpl;

import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.MailBoxService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MailBoxServiceImpl implements MailBoxService {
    @Autowired
    private MailBoxDao MailBoxDao;
    @Autowired
    private MailDao MailDao;

    @Override
    public MailBox getOneMailBox(Integer MailBoxId) {
        return MailBoxDao.getOneMailBox(MailBoxId);
    }

    public String addNewMailBox(Integer userId) {
        return MailBoxDao.addNewMailBox(userId);
    }

    public String updateMailBox(Integer MailBoxId, List<Integer> mails) {
        return MailBoxDao.updateMailBox(MailBoxId, mails);
    }

    public List<MailBox> getAllMailBoxs() {
        return MailBoxDao.getAllMailBoxs();
    }

    public String deleteMailBoxs(List<Integer> MailBoxIds) {
        return MailBoxDao.deleteMailBoxs(MailBoxIds);
    }

    public String deleteAll() {
        return MailBoxDao.deleteAll();
    }

    @Override
    public List<Mail> getOneUserMails(Integer MailBoxId) {
        List<Integer> mailIds = getOneMailBox(MailBoxId).getMailIds();
        List<Mail> userMails = new ArrayList<Mail>();
        for (int i = 0; i < mailIds.size(); i++) {
            Mail userMail = MailDao.getOneMail(mailIds.get(i));
            userMails.add(userMail);
        }

        return userMails;
    }
}