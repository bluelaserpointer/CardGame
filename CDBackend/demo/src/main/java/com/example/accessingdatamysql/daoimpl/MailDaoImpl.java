package com.example.accessingdatamysql.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.GlobalConstants;
import com.example.accessingdatamysql.dao.MailDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MailDaoImpl implements MailDao {
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private MailDetailsRepository mailDetailsRepository;

    @Override
    public Mail getOneMail(Integer MailId) {
        Mail Mail = mailRepository.getOne(MailId);
        Optional<MailDetails> MailDetails = mailDetailsRepository.findMailDetailsByMailIdEquals(MailId);
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
        // GlobalConstants.printIfDoDebug("new Mail has an Id of : " + n.getMailId());
        mailRepository.save(Mail);
        MailDetails MailDetails = new MailDetails(Mail.getMailId(), newMail.getMailDetails().getMailImg(),
                newMail.getMailDetails().getMailDescription());
        mailDetailsRepository.save(MailDetails);
        Mail.setMailDetails(MailDetails);
        return Mail;

    }

    public Mail updateMail(Mail updateMail) {

        Mail Mail = mailRepository.getOne(updateMail.getMailId());
        // GlobalConstants.printIfDoDebug("old Mail has an Id of : " + n.getMailId());
        Mail.setMailName(updateMail.getMailName());

        mailRepository.updateMailStatus(Mail, updateMail.getMailId());

        Optional<MailDetails> optMailDetails = mailDetailsRepository
                .findMailDetailsByMailIdEquals(updateMail.getMailId());
        MailDetails mailDetails = new MailDetails(updateMail.getMailId(), "", "");
        if (optMailDetails.isPresent()) {
            GlobalConstants.printIfDoDebug("Mail Exists");
            mailDetails = optMailDetails.get();
        } else {
            GlobalConstants.printIfDoDebug("Mail doesn't exist");
        }

        mailDetails.setMailDescription(updateMail.getMailDetails().getMailDescription());
        mailDetails.setMailImg(updateMail.getMailDetails().getMailImg());
        mailDetailsRepository.save(mailDetails);
        updateMail.setMailDetails(mailDetails);
        return updateMail;

    }

    public List<Mail> getAllMails() {
        //TODO: 可能还可以优化，这是每当要查询时才生成HashMap的版本
        final HashMap<Integer, MailDetails> mailIdAndDetails = new HashMap<>();
        for(MailDetails details : mailDetailsRepository.findAll()) {
            mailIdAndDetails.put(details.getMailId(), details);
        }
        final List<Mail> mails = mailRepository.findAll();
        mails.forEach(mail ->
                mail.setMailDetails(mailIdAndDetails.get(mail.getMailId())));
        return mails;
    }

    public String deleteMails(List<Integer> MailIds) {
        for (Integer mailId : MailIds) {
            mailRepository.deleteById(mailId);
            mailDetailsRepository.deleteMailDetailsByMailIdEquals(mailId);
        }
        return "Deleted Mails by id";
    }

    public String deleteAll() {
        mailRepository.deleteAll();
        mailDetailsRepository.deleteAll();
        return "Deleted All Mails";
    }

    public List<Mail> deleteMail(Integer mailId) {
        mailRepository.deleteById(mailId);
        mailDetailsRepository.deleteMailDetailsByMailIdEquals(mailId);
        return getAllMails();
    }

    @Override
    public JSONObject ListPage(Integer page_token, Integer page_size) {
        return this.ListPage(page_token, page_size, mailRepository, mail -> {
            mailDetailsRepository
                    .findMailDetailsByMailIdEquals(mail.getMailId())
                    .ifPresent(mail::setMailDetails);
            return mail;
        });
    }
}
