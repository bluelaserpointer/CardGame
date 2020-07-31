package com.example.myapplicationtest1.page;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.utils.Cache;
import com.example.myapplicationtest1.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

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
        super.setJump(R.id.toUserInfo_button, UserInfoPage.class);
        GHQ.setResource(getResources());
        Utils.setUserTopBarInfo(this);
        ((TextView)findViewById(R.id.userName)).setText(Cache.userName);
        ((TextView)findViewById(R.id.userLevel)).setText("LV: " + Cache.level);
        ((TextView)findViewById(R.id.userExp)).setText("exp: " + Cache.curExpPoint);
    }
}
