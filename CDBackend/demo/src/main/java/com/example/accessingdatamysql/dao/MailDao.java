package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface MailDao {
        Mail getOneMail(Integer MailId);

        // Optional<MailDetails> findOneDetail(Integer id);

        // void modifyStorage(Mail Mail);

        String addNewMail(String MailName, String mailImg, String mailDescription);

        String updateMail(Integer MailId, String MailName, String mailImg, String mailDescription);

        List<Mail> getAllMails();

        String deleteMails(List<Integer> MailIds);

        String deleteAll();

        List<Mail> deleteMail(Integer mailId);

}
