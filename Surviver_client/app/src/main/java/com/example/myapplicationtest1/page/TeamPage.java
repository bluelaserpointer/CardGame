package com.example.myapplicationtest1.page;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.pageParts.TeamTableAdapter;
import com.example.myapplicationtest1.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class TeamPage extends Page {
    static int onEditPos = -1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team);
        super.setJump(R.id.toItemStorage_Button, ItemStoragePage.class);
        super.setJump(R.id.toCardStorage_Button, CardStoragePage.class);
        super.setJump(R.id.toGallery_Button, GalleryPage.class);
        super.setJump(R.id.return_button, HomePage.class);
        Utils.setUserTopBarInfo(this);

        //formation edit
        final RecyclerView teamTable = findViewById(R.id.teamTableContent);
        teamTable.setLayoutManager(new LinearLayoutManager(TeamPage.this));
        teamTable.setAdapter(new TeamTableAdapter(TeamPage.this));
    }
    public static int getOnEditPos() {
        return onEditPos;
    }
    public static int setOnEditPos(int pos) {
        return onEditPos = pos;
    }
    public static HashMap<Integer, Integer> formation = new HashMap<>();
    public static void setFormationToOnEditPos(int ownCardId) {
        System.out.println("TeamPage: onEditPos(" + onEditPos + ")");
        for(Map.Entry<Integer, Integer> entry : formation.entrySet()) { //remove oldPosition
            if(entry.getValue().equals(ownCardId)) {
                if(entry.getKey().equals(onEditPos)) { //no effect edit
                    System.out.println("TeamPage: duplicated edit");
                    return;
                }
                System.out.println("TeamPage: moving the same at " + entry.getKey());
                formation.remove(entry.getKey());
                break;
            }
        }
        System.out.println("TeamPage: set at " + onEditPos);
        formation.put(onEditPos, ownCardId);
        onEditPos = -1;
    }
}
