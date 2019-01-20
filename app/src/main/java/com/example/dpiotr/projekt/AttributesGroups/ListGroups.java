package com.example.dpiotr.projekt.AttributesGroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.Attributes.AddAttributeActivity;
import com.example.dpiotr.projekt.Attributes.EditAttributeActivity;
import com.example.dpiotr.projekt.Attributes.GetAllAttributesRequest;
import com.example.dpiotr.projekt.Attributes.ListAttributesActivity;
import com.example.dpiotr.projekt.DividerItemDecoration;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.RecyclerItemClickListener;
import com.example.dpiotr.projekt.Registry.EditActivity;
import com.example.dpiotr.projekt.RegistryAttributes.GetAllRegistryAttributesRequest;
import com.example.dpiotr.projekt.RegistryAttributes.GetRegistryAttributesRequest;
import com.example.dpiotr.projekt.Rooms.GetRoomRequest;
import com.example.dpiotr.projekt.TokenSaver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class ListGroups extends AppCompatActivity {

    private List<AttributesGroup> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private AttributesGroupAdapter adapter;

    JSONArray allAttributesGroups;

    List<Integer> attributesIDs = new ArrayList<>();
    List<Integer> groupsIDs = new ArrayList<>();
    List<Integer> attributesGroupsIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent inte = getIntent();
        int registryID = inte.getIntExtra("registryID",0);
        String allRegAttS = inte.getStringExtra("allRegAtt");
        String allGroupsS = inte.getStringExtra("allGroups");
        String allAttributesGroupsS = inte.getStringExtra("allAttributesGroups");

        try {
            JSONArray allRegAtt = new JSONArray(allRegAttS);
            JSONArray attributes = new JSONArray(allGroupsS);
            JSONArray allAttributesGroups = new JSONArray(allAttributesGroupsS);
            for (int i = 0; i < allRegAtt.length(); i++) {
                if (allRegAtt.getJSONObject(i).getString("registry_id").equals(String.valueOf(registryID))) {
                    attributesIDs.add(Integer.parseInt(allRegAtt.getJSONObject(i).getString("attribute_id")));
                }
            }

            for (int i = 0; i < attributes.length(); i++) {
                for (int j = 0; j < attributesIDs.size(); j++) {
                    if (attributes.getJSONObject(i).getString("id").equals(String.valueOf(attributesIDs.get(j))))
                        groupsIDs.add(Integer.parseInt(attributes.getJSONObject(i).getString("id")));
                }
            }

            for (int idx = 0; idx < groupsIDs.size(); idx++) {
                for (int idx1 = 0; idx1 < groupsIDs.size(); idx1++) {
                    int r = groupsIDs.get(idx);
                    int r1 = groupsIDs.get(idx1);
                    if (idx != idx1) {
                        if (r == r1) {
                            groupsIDs.remove(idx1);
                            idx1--;
                        }
                    }
                }
            }

            for(int i=0;i<groupsIDs.size();i++){
                for(int j=0;j<attributes.length();j++){
                    if(groupsIDs.get(i)==Integer.parseInt(attributes.getJSONObject(j).getString("id")))
                        attributesGroupsIDs.add(Integer.parseInt(attributes.getJSONObject(j).getString("group_id")));
                }
            }

            for(int i=0;i<attributesGroupsIDs.size();i++){
                for(int j=0;j<allAttributesGroups.length();j++) {
                    if (attributesGroupsIDs.get(i)==Integer.parseInt(allAttributesGroups.getJSONObject(j).getString("id"))) {
                        AttributesGroup attgrp = new AttributesGroup(allAttributesGroups.getJSONObject(j).getString("name"));
                        list.add(attgrp);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_list_groups);
        recyclerView = (RecyclerView) findViewById(R.id.groups_recycler_view);

        adapter = new AttributesGroupAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Intent intent = new Intent(ListGroups.this, ListAttributesActivity.class);

                                    JSONObject jsonResponse = new JSONObject(response);
                                    JSONObject obj = jsonResponse.getJSONObject("result");

                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                       // GetRoomRequest request = new GetRoomRequest(id,responseListener, TokenSaver.getToken(ListAttributesActivity.this));
                        //RequestQueue queue = Volley.newRequestQueue(ListAttributesActivity.this);
                        //queue.add(request);
                    }
                })
        );

        FloatingActionButton myFab = (FloatingActionButton)  findViewById(R.id.attributes_myFAB);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ListGroups.this, AddGroupActvity.class);
                startActivity(myIntent);
            }
        });
    }

}
