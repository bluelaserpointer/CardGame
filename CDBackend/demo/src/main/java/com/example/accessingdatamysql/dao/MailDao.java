package com.example.accessingdatamysql.dao;

import java.util.List;

import com.example.accessingdatamysql.Classes.JSONPagination;
import com.example.accessingdatamysql.entity.*;

public interface MailDao extends JSONPagination {
        Mail getOneMail(Integer MailId);

        // Optional<MailDetails> findOneDetail(Integer id);

        // void modifyStorage(Mail Mail);

        Mail addNewMail(Mail newMail);

        Mail updateMail(Mail updateMail);

        List<Mail> getAllMails();

        String deleteMails(List<Integer> MailIds);

        String deleteAll();

        List<Mail> deleteMail(Integer mailId);

}
