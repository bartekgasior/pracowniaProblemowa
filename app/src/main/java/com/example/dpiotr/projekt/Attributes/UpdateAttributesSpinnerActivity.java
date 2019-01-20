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
import com.example.dpiotr.projekt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class UpdateAttributesSpinnerActivity extends AppCompatActivity {
    JSONArray allAtts;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_spinner);
        getAllAttributes();

        Intent intent = getIntent();
        final int registryID = intent.getIntExtra("registryID",0);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(UpdateAttributesSpinnerActivity.this, AddAttributeActivity.class);
                intent.putExtra("registryID", registryID);
                intent.putExtra("allAtts", allAtts.toString());
                UpdateAttributesSpinnerActivity.this.startActivity(intent);
                finish();
            }
        }, secondsDelayed * 1000);
    }

    public void getAllAttributes(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    allAtts = jsonResponse.getJSONArray("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetAllAttributesRequest request1 = new GetAllAttributesRequest (responseListener,getToken(UpdateAttributesSpinnerActivity.this));
        RequestQueue queue1 = Volley.newRequestQueue(UpdateAttributesSpinnerActivity.this);
        queue1.add(request1);
    }
}
