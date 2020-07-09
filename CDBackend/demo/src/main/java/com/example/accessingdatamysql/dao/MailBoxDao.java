package com.example.accessingdatamysql.dao;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.example.accessingdatamysql.entity.*;

public interface MailBoxDao {
        MailBox getOneMailBox(Integer MailBoxId);

        String addNewMailBox(Integer userId);

        String updateMailBox(Integer MailBoxId, List<Mail> mails);

        List<MailBox> getAllMailBoxs();

        String deleteMailBoxs(List<Integer> MailBoxIds);

        String deleteAll();

}
