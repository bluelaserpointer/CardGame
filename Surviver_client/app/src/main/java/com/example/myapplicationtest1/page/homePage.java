package com.example.myapplicationtest1.page;

import android.widget.Button;

import com.example.myapplicationtest1.FullscreenActivity;
import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;

import org.json.JSONArray;
import org.json.JSONException;

public class homePage{
    public static void homePageInit(FullscreenActivity screen) throws JSONException {

        // Fetching data of the current logged in user
        String data = HttpClient.doGet("http://192.168.175.1:8080/user/getAllUsers");
        JSONArray arr = new JSONArray(data);

        // Sets the text of corresponding values
        final Button grade_button = screen.findViewById(R.id.grade_button);
        grade_button.setText(arr.getJSONObject(0).get("grade").toString());
        final Button stamina_button = screen.findViewById(R.id.stamina_button);
        stamina_button.setText(arr.getJSONObject(0).get("stamina").toString());
        final Button money_button = screen.findViewById(R.id.money_button);
        money_button.setText(arr.getJSONObject(0).get("money").toString());

    }
}
