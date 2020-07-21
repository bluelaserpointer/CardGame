package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;
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
        super.setTouchEvent(R.id.button1_1, () -> jumpToSelectCard(0));
        super.setTouchEvent(R.id.button1_2, () -> jumpToSelectCard(1));
        super.setTouchEvent(R.id.button1_3, () -> jumpToSelectCard(2));
        super.setTouchEvent(R.id.button1_4, () -> jumpToSelectCard(3));
        super.setTouchEvent(R.id.button1_5, () -> jumpToSelectCard(4));
    }
    private void jumpToSelectCard(int onEditPos) {
        TeamPage.onEditPos = onEditPos;
        Page.jump(this, CardStoragePage.class);
    }
    public static int getOnEditPos() {
        return onEditPos;
    }
    public static HashMap<Integer, Integer> formation = new HashMap<>();
    public static void setFormationToOnEditPos(int ownCardId) {
        for(Map.Entry<Integer, Integer> entry : formation.entrySet()) { //remove oldPosition
            if(entry.getValue().equals(ownCardId)) {
                if(entry.getKey().equals(onEditPos)) //no effect edit
                    return;
                formation.remove(entry.getKey());
                break;
            }
        }
        formation.put(onEditPos, ownCardId);
        onEditPos = -1;
    }
}
