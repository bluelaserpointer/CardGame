package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.MailBoxDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.io.Console;
import java.util.*;

@Repository
public class MailBoxDaoImpl implements MailBoxDao {
    @Autowired
    private MailBoxRepository mailBoxRepository;

    @Override
    public MailBox getOneMailBox(Integer MailBoxId) {
        return mailBoxRepository.getOne(MailBoxId);
    }

    public String addNewMailBox(Integer userId) {

        MailBox MailBox = new MailBox(userId);
        // GlobalConstants.printIfDoDebug("new MailBox has an Id of : " + n.getMailBoxId());
        mailBoxRepository.save(MailBox);
        return "Saved MailBox";

    }

    public String updateMailBox(Integer MailBoxId, List<Integer> mails) {

        MailBox MailBox = mailBoxRepository.getOne(MailBoxId);
        // GlobalConstants.printIfDoDebug("old MailBox has an Id of : " + n.getMailBoxId());
        MailBox.setMails(mails);

        mailBoxRepository.updateMailBoxStatus(MailBox, MailBoxId);
        return "modified MailBox: " + MailBox.getMailBoxId();

    }

    public List<MailBox> getAllMailBoxs() {
        return mailBoxRepository.findAll();
    }

    public String deleteMailBoxs(List<Integer> MailBoxIds) {
        for (Integer mailBoxId : MailBoxIds) {
            mailBoxRepository.deleteById(mailBoxId);
        }
        return "Deleted MailBoxs by id";
    }

    public String deleteAll() {
        mailBoxRepository.deleteAll();
        return "Deleted All MailBoxs";
    }
}
