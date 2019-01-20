package com.example.dpiotr.projekt.Attributes;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.AttributesGroups.GetAllAttributesGroupsRequest;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.RegistryAttributes.GetAllRegistryAttributesRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class UpdateAttributesListActivity extends AppCompatActivity {
    JSONArray allRegAtt;
    JSONArray allGroups;
    JSONArray allAttributesGroups;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllAttributes();
        getAllAttributesGroups();
        getAllRegistryAttributes();
        setContentView(R.layout.activity_update_attributes_list);
        Intent intent = getIntent();
        final int registryID = intent.getIntExtra("registryID",0);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(UpdateAttributesListActivity.this, ListAttributesActivity.class);
                intent.putExtra("registryID", registryID);
                intent.putExtra("allRegAtt", allRegAtt.toString());
                intent.putExtra("allGroups", allGroups.toString());
                intent.putExtra("allAttributesGroups", allAttributesGroups.toString());
                UpdateAttributesListActivity.this.startActivity(intent);
                finish();
            }
        }, secondsDelayed * 1000);
    }

    public void getAllRegistryAttributes(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    allRegAtt = jsonResponse.getJSONArray("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetAllRegistryAttributesRequest request1 = new GetAllRegistryAttributesRequest(responseListener,getToken(UpdateAttributesListActivity.this));
        RequestQueue queue = Volley.newRequestQueue(UpdateAttributesListActivity.this);
        queue.add(request1);
    }

    public void getAllAttributes(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    allGroups = jsonResponse.getJSONArray("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetAllAttributesRequest request1 = new GetAllAttributesRequest (responseListener,getToken(UpdateAttributesListActivity.this));
        RequestQueue queue = Volley.newRequestQueue(UpdateAttributesListActivity.this);
        queue.add(request1);
    }

    public void getAllAttributesGroups(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    allAttributesGroups = jsonResponse.getJSONArray("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetAllAttributesGroupsRequest request1 = new GetAllAttributesGroupsRequest (responseListener,getToken(UpdateAttributesListActivity.this));
        RequestQueue queue = Volley.newRequestQueue(UpdateAttributesListActivity.this);
        queue.add(request1);
    }
}
