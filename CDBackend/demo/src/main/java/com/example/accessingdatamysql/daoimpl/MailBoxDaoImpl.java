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
    private MailBoxRepository MailBoxRepository;

    @Override
    public MailBox getOneMailBox(Integer MailBoxId) {
        MailBox MailBox = MailBoxRepository.getOne(MailBoxId);
        return MailBox;
    }

    public String addNewMailBox(Integer userId) {

        MailBox MailBox = new MailBox(userId);
        // System.out.println("new MailBox has an Id of : " + n.getMailBoxId());
        MailBoxRepository.save(MailBox);
        return "Saved MailBox";

    }

    public String updateMailBox(Integer MailBoxId, List<Integer> mails) {

        MailBox MailBox = MailBoxRepository.getOne(MailBoxId);
        // System.out.println("old MailBox has an Id of : " + n.getMailBoxId());
        MailBox.setMails(mails);

        MailBoxRepository.updateMailBoxStatus(MailBox, MailBoxId);
        return "modified MailBox: " + MailBox.getMailBoxId();

    }

    public List<MailBox> getAllMailBoxs() {
        List<MailBox> MailBoxs = MailBoxRepository.findAll();
        return MailBoxs;
    }

    public String deleteMailBoxs(List<Integer> MailBoxIds) {
        for (int i = 0; i < MailBoxIds.size(); i++) {
            MailBoxRepository.deleteById(MailBoxIds.get(i));
        }
        return "Deleted MailBoxs by id";
    }

    public String deleteAll() {
        MailBoxRepository.deleteAll();
        return "Deleted All MailBoxs";
    }
}