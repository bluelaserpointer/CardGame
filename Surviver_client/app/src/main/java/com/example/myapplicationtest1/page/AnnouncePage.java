package com.example.myapplicationtest1.page;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class AnnouncePage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announce);
        super.setJump(R.id.return_button, HomePage.class);

        try {
            final JSONObject json = new JSONObject(HttpClient.doGetShort(Urls.getActivity(1)));
            super.setText_TextView(R.id.activityTitle, json.getString("activityName"));
            super.setText_TextView(R.id.activityTime, json.getString("start"));
            final JSONObject detailJson = json.getJSONObject("activityDetails");
            final String imgBase64 = detailJson.getString("activityImg");
            super.setText_TextView(R.id.activityDescription, detailJson.getString("activityDescription"));
            if(!imgBase64.isEmpty()) {
                System.out.println(imgBase64);
                final byte[] decodedString = Base64.decode(imgBase64, Base64.DEFAULT);
                ((ImageView) super.findViewById(R.id.activityImage)).setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
