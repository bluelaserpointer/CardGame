package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
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

    public Mail addNewMail(Mail newMail) {
        Mail Mail = new Mail(newMail.getMailName());
        // System.out.println("new Mail has an Id of : " + n.getMailId());
        MailRepository.save(Mail);
        MailDetails MailDetails = new MailDetails(Mail.getMailId(), newMail.getMailDetails().getMailImg(),
                newMail.getMailDetails().getMailDescription());
        MailDetailsRepository.save(MailDetails);
        Mail.setMailDetails(MailDetails);
        return Mail;

    }

    public Mail updateMail(Mail updateMail) {

        Mail Mail = MailRepository.getOne(updateMail.getMailId());
        // System.out.println("old Mail has an Id of : " + n.getMailId());
        Mail.setMailName(updateMail.getMailName());

        MailRepository.updateMailStatus(Mail, updateMail.getMailId());

        Optional<MailDetails> optMailDetails = MailDetailsRepository
                .findMailDetailsByMailIdEquals(updateMail.getMailId());
        MailDetails mailDetails = new MailDetails(updateMail.getMailId(), "", "");
        if (optMailDetails.isPresent()) {
            System.out.println("Mail Exists");
            mailDetails = optMailDetails.get();
        } else {
            System.out.println("Mail doesn't exist");
        }

        mailDetails.setMailDescription(updateMail.getMailDetails().getMailDescription());
        mailDetails.setMailImg(updateMail.getMailDetails().getMailImg());
        MailDetailsRepository.save(mailDetails);
        updateMail.setMailDetails(mailDetails);
        return updateMail;

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

    public List<Mail> deleteMail(Integer mailId) {
        MailRepository.deleteById(mailId);
        MailDetailsRepository.deleteMailDetailsByMailIdEquals(mailId);
        return getAllMails();
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        JSONObject response = new JSONObject();

        // get the result data
        Integer start = (page_token - 1) * page_size;
        // Integer end = page_token * page_size - 1;
        List<Mail> mails = MailRepository.ListPage(start, page_size);
        for (int i = 0; i < mails.size(); i++) {
            Mail Mail = mails.get(i);
            Optional<MailDetails> MailDetails = MailDetailsRepository.findMailDetailsByMailIdEquals(Mail.getMailId());
            MailDetails.ifPresent(Mail::setMailDetails);
            mails.set(i, Mail);
        }

        // get the nextPageToken
        Integer nextPageToken;
        if ((MailRepository.findAll().size() - (page_token * page_size)) <= 0) {
            response.put("nextPageToken", "");
        } else {
            nextPageToken = page_token + 1;
            response.put("nextPageToken", nextPageToken);
        }

        // get the total pages of the result
        Integer totalPages = MailRepository.findAll().size() / page_size;
        if ((totalPages - page_size * totalPages) > 0) {
            totalPages += 1;
        }
        response.put("result", mails);
        response.put("totalPages", totalPages);

        return response;
    }
}
