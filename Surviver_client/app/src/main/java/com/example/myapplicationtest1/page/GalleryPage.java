package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.utils.Utils;

public class GalleryPage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        super.setJump(R.id.toTeam_Button, TeamPage.class);
        super.setJump(R.id.toItemStorage_Button, ItemStoragePage.class);
        super.setJump(R.id.toCardStorage_Button, CardStoragePage.class);
        super.setJump(R.id.return_button, HomePage.class);
        Utils.setUserTopBarInfo(this);
    }
}
