package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.MailDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.io.Console;
import java.util.*;

@Repository
public class MailDaoImpl implements MailDao {
    @Autowired
    private MailRepository MailRepository;
    @Autowired
    private MailDetailsRepository MailDetailsRepository;

    @Override
    public Mail getOneMail(Integer MailId) {
        Mail Mail = MailRepository.getOne(MailId);
        Optional<MailDetails> MailDetails = MailDetailsRepository.findMailDetailsByMailIdEquals(MailId);
        MailDetails.ifPresent(Mail::setMailDetails);
        return Mail;
    }

    // public void modifyStorage(Mail Mail) {
    // MailRepository.updateStorageStatus(Mail.getStorage(), Mail.getMailId());
    // return;
    // }

    // public MailDetails findOneDetail(Integer id) {
    // return MailDetailsRepository.findById(id);
    // }

    public String addNewMail(String MailName, String mailImg, String mailDescription) {
        System.out.println(mailDescription);
        Mail Mail = new Mail(MailName);
        // System.out.println("new Mail has an Id of : " + n.getMailId());
        MailRepository.save(Mail);
        MailDetails MailDetails = new MailDetails(Mail.getMailId(), mailImg, mailDescription);
        MailDetailsRepository.save(MailDetails);
        return "Saved Mail";

    }

    public String updateMail(Integer MailId, String MailName, String mailImg, String mailDescription) {

        Mail Mail = MailRepository.getOne(MailId);
        // System.out.println("old Mail has an Id of : " + n.getMailId());
        Mail.setMailName(MailName);

        MailRepository.updateMailStatus(Mail, MailId);

        Optional<MailDetails> optMailDetails = MailDetailsRepository.findMailDetailsByMailIdEquals(MailId);
        MailDetails mailDetails = new MailDetails(MailId, "", "");
        if (optMailDetails.isPresent()) {
            System.out.println("Mail Exists");
            mailDetails = optMailDetails.get();
        } else {
            System.out.println("Mail doesn't exist");
        }

        mailDetails.setMailDescription(mailDescription);
        mailDetails.setMailImg(mailImg);
        MailDetailsRepository.save(mailDetails);
        return "modified Mail: " + Mail.getMailName();

    }

    public List<Mail> getAllMails() {
        List<Mail> Mails = MailRepository.findAll();
        for (int i = 0; i < Mails.size(); i++) {
            Mail Mail = Mails.get(i);
            Optional<MailDetails> MailDetails = MailDetailsRepository.findMailDetailsByMailIdEquals(Mail.getMailId());
            MailDetails.ifPresent(Mail::setMailDetails);
            Mails.set(i, Mail);
        }
        return Mails;
    }

    public String deleteMails(List<Integer> MailIds) {
        for (int i = 0; i < MailIds.size(); i++) {
            MailRepository.deleteById(MailIds.get(i));
            MailDetailsRepository.deleteMailDetailsByMailIdEquals(MailIds.get(i));
        }
        return "Deleted Mails by id";
    }

    public String deleteAll() {
        MailRepository.deleteAll();
        MailDetailsRepository.deleteAll();
        return "Deleted All Mails";
    }

    public List<Mail> deleteMail(Integer mailId){
        MailRepository.deleteById(mailId);
        MailDetailsRepository.deleteMailDetailsByMailIdEquals(mailId);
        return getAllMails();
    }
}
