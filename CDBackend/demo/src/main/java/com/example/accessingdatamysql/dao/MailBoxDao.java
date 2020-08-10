package com.example.accessingdatamysql.dao;

import java.util.List;

import com.example.accessingdatamysql.entity.*;

public interface MailBoxDao {
        MailBox getOneMailBox(Integer MailBoxId);

        String addNewMailBox(Integer userId);

        String updateMailBox(Integer MailBoxId, List<Integer> mails);

        List<MailBox> getAllMailBoxs();

        String deleteMailBoxs(List<Integer> MailBoxIds);

        String deleteAll();

}
