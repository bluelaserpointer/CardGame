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
        final JSONArray arr = new JSONArray(HttpClient.doGetShort("user/getAllUsers"));
        // Sets the text of corresponding values
        ((Button)findViewById(R.id.grade_button).findViewById(R.id.grade_button)).setText(arr.getJSONObject(0).get("grade").toString());
        ((Button)findViewById(R.id.stamina_button)).setText(arr.getJSONObject(0).get("stamina").toString());
        ((Button)findViewById(R.id.money_button)).setText(arr.getJSONObject(0).get("money").toString());

    }
}
