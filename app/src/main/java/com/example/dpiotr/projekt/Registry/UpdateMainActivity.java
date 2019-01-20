package com.example.dpiotr.projekt.Registry;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.Attributes.ListAttributesActivity;
import com.example.dpiotr.projekt.Attributes.UpdateAttributesListActivity;
import com.example.dpiotr.projekt.ChooseOption.ChooseOptionActvity;
import com.example.dpiotr.projekt.ChooseOption.GetAllStatesRequest;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.Rooms.GetAllRoomsRequest;
import com.example.dpiotr.projekt.TokenSaver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateMainActivity extends AppCompatActivity {

    JSONArray rooms;
    JSONArray states;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRooms();
        setContentView(R.layout.activity_update_main);
        Intent intent = getIntent();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(UpdateMainActivity.this, MainActivity.class);
                intent.putExtra("statesArray",states.toString());
                intent.putExtra("roomsArray",rooms.toString());
                UpdateMainActivity.this.startActivity(intent);
                finish();
            }
        }, secondsDelayed * 1000);
    }

    public void getStates(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse1 = new JSONObject(response);
                    states = jsonResponse1.getJSONArray("result");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetAllStatesRequest request1 = new GetAllStatesRequest(responseListener,TokenSaver.getToken(getApplicationContext()));
        RequestQueue queue1 = Volley.newRequestQueue(UpdateMainActivity.this);
        queue1.add(request1);
    }

    public void getRooms(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    rooms = jsonResponse.getJSONArray("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetAllRoomsRequest request = new GetAllRoomsRequest(responseListener,TokenSaver.getToken(getApplicationContext()));
        RequestQueue queue = Volley.newRequestQueue(UpdateMainActivity.this);
        queue.add(request);
    }
}
