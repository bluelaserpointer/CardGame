package com.example.myapplicationtest1.page;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.pageParts.ChapterListAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class MapPage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        super.setJump(R.id.return_button, HomePage.class);
        final RecyclerView chapterRecyclerView = findViewById(R.id.chapterList);
        chapterRecyclerView.setLayoutManager(new LinearLayoutManager(MapPage.this));
        chapterRecyclerView.setAdapter(new ChapterListAdapter(MapPage.this));

        final View mapSelector = findViewById(R.id.mapSelector);
        new Timer().schedule(new TimerTask() {
            public void run() {
                if(mapSelector != null)
                    mapSelector.invalidate();
            }
        },200,50);
    }
}
