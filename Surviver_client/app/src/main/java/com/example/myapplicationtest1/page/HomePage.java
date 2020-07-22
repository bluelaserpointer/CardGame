package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.utils.Utils;

import org.json.JSONException;

public class HomePage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        super.setJump(R.id.toBattle_button, MapPage.class);
        super.setJump(R.id.toOption_button, OptionPage.class);
        super.setJump(R.id.toShop_button, LottePage.class);
        super.setJump(R.id.toMail_button, MailPage.class);
        super.setJump(R.id.toAnnounce_button, AnnouncePage.class);
        super.setJump(R.id.toBag_button, TeamPage.class);
        GHQ.setResource(getResources());
        Utils.setUserTopBarInfo(this);
    }
}
