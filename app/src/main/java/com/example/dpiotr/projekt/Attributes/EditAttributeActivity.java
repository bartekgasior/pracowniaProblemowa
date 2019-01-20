package com.example.dpiotr.projekt.Attributes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.AttributesGroups.GetAllAttributesGroupsRequest;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.Registry.EditActivity;
import com.example.dpiotr.projekt.RegistryAttributes.AddRegistryAttributesRequest;
import com.example.dpiotr.projekt.RegistryAttributes.EditRegistryAttributesRequest;
import com.example.dpiotr.projekt.RegistryAttributes.GetAllRegistryAttributesRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class EditAttributeActivity extends AppCompatActivity {

    JSONArray allRegAtt;
    JSONArray allGroups;
    JSONArray allAttributesGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_attribute);

        getAllRegistryAttributes();
        getAllAttributes();
        getAllAttributesGroups();

        Intent intent=getIntent();

        final int registryID = intent.getIntExtra("registryID",0);
        final String name=intent.getStringExtra("name");
        final String valueTMP = intent.getStringExtra("value");
        final int id=intent.getIntExtra("id",0);
        final int attID=intent.getIntExtra("attID",0);
        final TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(name);
        final EditText etValue = (EditText) findViewById(R.id.etValue);
        // etValue.setText(intent.getStringExtra("value"));
        final Button bEdit = (Button) findViewById(R.id.bEdit);

        System.out.println("attid " + attID + ", regattid " + id + ", regid " + registryID);

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(EditAttributeActivity.this, UpdateAttributesListActivity.class);
                        intent.putExtra("registryID", registryID);
                        intent.putExtra("allRegAtt", allRegAtt.toString());
                        intent.putExtra("allGroups", allGroups.toString());
                        intent.putExtra("allAttributesGroups", allAttributesGroups.toString());
                        EditAttributeActivity.this.startActivity(intent);
                    }
                };
                EditRegistryAttributesRequest request = new EditRegistryAttributesRequest(registryID, attID, etValue.getText().toString(), id , responseListener,getToken(getApplicationContext()));
                RequestQueue queue = Volley.newRequestQueue(EditAttributeActivity.this);
                queue.add(request);
            }
        });
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
        GetAllRegistryAttributesRequest request1 = new GetAllRegistryAttributesRequest(responseListener,getToken(getApplicationContext()));
        RequestQueue queue1 = Volley.newRequestQueue(EditAttributeActivity.this);
        queue1.add(request1);
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
        GetAllAttributesRequest request1 = new GetAllAttributesRequest (responseListener,getToken(getApplicationContext()));
        RequestQueue queue1 = Volley.newRequestQueue(EditAttributeActivity.this);
        queue1.add(request1);
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
        GetAllAttributesGroupsRequest request1 = new GetAllAttributesGroupsRequest (responseListener,getToken(getApplicationContext()));
        RequestQueue queue1 = Volley.newRequestQueue(EditAttributeActivity.this);
        queue1.add(request1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}