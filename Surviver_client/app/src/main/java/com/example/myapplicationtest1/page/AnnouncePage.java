package com.example.myapplicationtest1.page;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.pageParts.ActivityListAdapter;
import com.example.myapplicationtest1.utils.Cache;
import com.example.myapplicationtest1.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class AnnouncePage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announce);
        super.setJump(R.id.return_button, HomePage.class);

        final RecyclerView activityListViewer = findViewById(R.id.activityList);
        activityListViewer.setLayoutManager(new LinearLayoutManager(AnnouncePage.this));
        activityListViewer.setAdapter(new ActivityListAdapter(AnnouncePage.this));
    }
    public void setActivityInfo(int activityId) {
        if(activityId == -1) {
            return;
        }
        final Cache.Activity activity = Cache.activities.get(activityId);
        super.setText_TextView(R.id.activityTitle, activity.time);
        super.setText_TextView(R.id.activityTime, activity.time);
        final String imgBase64 = activity.imgBase64;
        super.setText_TextView(R.id.activityDescription, activity.description);
        if(!imgBase64.isEmpty()) {
            try {
                final byte[] decodedString = Base64.decode(imgBase64, Base64.DEFAULT);
                ((ImageView) super.findViewById(R.id.activityImage)).setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
            } catch (RuntimeException e) {
                System.out.println("AnnouncePage: Fail to load " + imgBase64);
                e.printStackTrace();
            }
        }
    }
}
