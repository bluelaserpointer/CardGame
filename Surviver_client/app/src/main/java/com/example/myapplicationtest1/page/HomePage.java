package com.example.myapplicationtest1.page;

import android.os.Bundle;
import android.widget.Button;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;

import org.json.JSONArray;
import org.json.JSONException;

public class HomePage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        super.setJump(R.id.toBattle_button, MapPage.class);
        super.setJump(R.id.toOption_button, OptionPage.class);
        super.setJump(R.id.toShop_button, ShopPage.class);
        super.setJump(R.id.toMail_button, MailPage.class);
        super.setJump(R.id.toAnnounce_button, AnnouncePage.class);
        super.setJump(R.id.toBag_button, TeamPage.class);
        //data fetch
        try {
            fetchData();
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    public void fetchData() throws JSONException {
        // Fetching data of the current logged in user
        String data = HttpClient.doGet("http://192.168.175.1:8080/user/getAllUsers");
        JSONArray arr = new JSONArray(data);
        System.out.println("Hi");
        // Sets the text of corresponding values
        final Button grade_button = findViewById(R.id.grade_button);
        grade_button.setText(arr.getJSONObject(0).get("grade").toString());
        final Button stamina_button = findViewById(R.id.stamina_button);
        stamina_button.setText(arr.getJSONObject(0).get("stamina").toString());
        final Button money_button = findViewById(R.id.money_button);
        money_button.setText(arr.getJSONObject(0).get("money").toString());

    }
}
