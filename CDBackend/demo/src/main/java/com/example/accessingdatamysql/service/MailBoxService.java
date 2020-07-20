package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.*;

import java.util.List;

public interface MailBoxService {
        MailBox getOneMailBox(Integer MailBoxId);

        String addNewMailBox(Integer userId);

        String updateMailBox(Integer MailBoxId, List<Integer> mails);

        List<MailBox> getAllMailBoxs();

        String deleteMailBoxs(List<Integer> MailBoxIds);

        String deleteAll();
}