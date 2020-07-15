package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;

public class CardStoragePage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardstorage);
        super.setJump(R.id.toTeam_Button, TeamPage.class);
        super.setJump(R.id.toItemStorage_Button, ItemStoragePage.class);
        super.setJump(R.id.toGallery_Button, GalleryPage.class);
        super.setJump(R.id.return_button, HomePage.class);
    }
}
