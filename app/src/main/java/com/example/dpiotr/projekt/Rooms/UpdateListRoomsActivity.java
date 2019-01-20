package com.example.dpiotr.projekt.Rooms;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.Attributes.AddAttributeActivity;
import com.example.dpiotr.projekt.Attributes.UpdateAttributesSpinnerActivity;
import com.example.dpiotr.projekt.ChooseOption.ChooseOptionActvity;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.Registry.GetAllRegistryRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class UpdateListRoomsActivity extends AppCompatActivity {
    ProgressBar progressBar;
    JSONArray registry;
    JSONArray rooms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_list_rooms);
        getSupportActionBar().setTitle(getResources().getString(R.string.processing));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getRegistry();
        getRooms();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(UpdateListRoomsActivity.this, ListRoomsActivity.class);
                intent.putExtra("registryArray",registry.toString());
                intent.putExtra("roomsArray",rooms.toString());
                UpdateListRoomsActivity.this.startActivity(intent);
                finish();
            }
        }, secondsDelayed * 1000);
    }

    public void getRegistry(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    registry = jsonResponse.getJSONArray("result");
                    System.out.println("rozmiar registry " + registry.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetAllRegistryRequest request = new GetAllRegistryRequest(responseListener,getToken(UpdateListRoomsActivity.this));
        RequestQueue queue = Volley.newRequestQueue(UpdateListRoomsActivity.this);
        queue.add(request);
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
        GetAllRoomsRequest request = new GetAllRoomsRequest(responseListener,getToken(UpdateListRoomsActivity.this));
        RequestQueue queue = Volley.newRequestQueue(UpdateListRoomsActivity.this);
        queue.add(request);
    }
}
