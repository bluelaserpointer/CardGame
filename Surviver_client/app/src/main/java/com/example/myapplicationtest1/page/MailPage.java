package com.example.myapplicationtest1.page;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.pageParts.MailListAdapter;
import com.example.myapplicationtest1.utils.Cache;

public class MailPage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail);
        super.setJump(R.id.return_button, HomePage.class);

        final RecyclerView mailListViewer = findViewById(R.id.mailList);
        mailListViewer.setLayoutManager(new LinearLayoutManager(MailPage.this));
        mailListViewer.setAdapter(new MailListAdapter(MailPage.this));
    }
    public void setMailInfo(int mailId) {
        if(mailId == -1) {
            return;
        }
        final Cache.Mail mail = Cache.mails.get(mailId);
        super.setText_TextView(R.id.mailTime, mail.time);
        super.setText_TextView(R.id.mailTime, mail.time);
//        final String imgBase64 = mail.imgBase64;
        super.setText_TextView(R.id.mailContent, mail.content);
//        if(!imgBase64.isEmpty()) {
//            try {
//                final byte[] decodedString = Base64.decode(imgBase64, Base64.DEFAULT);
//                ((ImageView) super.findViewById(R.id.activityImage)).setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
//            } catch (RuntimeException e) {
//                System.out.println("AnnouncePage: Fail to load " + imgBase64);
//                e.printStackTrace();
//            }
//        }
    }
}
