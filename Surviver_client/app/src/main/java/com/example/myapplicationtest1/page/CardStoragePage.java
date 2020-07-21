package com.example.myapplicationtest1.page;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.pageParts.CardListAdapter;
import com.example.myapplicationtest1.utils.Utils;

public class CardStoragePage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardstorage);
        super.setJump(R.id.toTeam_Button, TeamPage.class);
        super.setJump(R.id.toItemStorage_Button, ItemStoragePage.class);
        super.setJump(R.id.toGallery_Button, GalleryPage.class);
        super.setJump(R.id.return_button, HomePage.class);
        final RecyclerView cardStorageViewer = findViewById(R.id.cardStorageContent);
        cardStorageViewer.setLayoutManager(new LinearLayoutManager(CardStoragePage.this));
        cardStorageViewer.setAdapter(new CardListAdapter(CardStoragePage.this));
        Utils.setUserTopBarInfo(this);
    }
}
