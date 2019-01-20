package com.example.dpiotr.projekt.Attributes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.AttributesGroups.GetAllAttributesGroupsRequest;
import com.example.dpiotr.projekt.DividerItemDecoration;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.RecyclerItemClickListener;
import com.example.dpiotr.projekt.RegistryAttributes.GetAllRegistryAttributesRequest;
import com.example.dpiotr.projekt.RegistryAttributes.GetRegistryAttributesRequest;
import com.example.dpiotr.projekt.Rooms.GetRoomRequest;
import com.example.dpiotr.projekt.Rooms.RoomsAdapter;
import com.example.dpiotr.projekt.TokenSaver;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class ListAttributesActivity extends AppCompatActivity {
    FloatingActionButton menu1,menu2;

    private List<Attribute> attributesList = new ArrayList<>();
    private List<Integer> registryAttributesIDList = new ArrayList<>();
    List<Integer> attributesIDs = new ArrayList<>();
    private RecyclerView recyclerView;
    private AttributesAdapter adapter;

    private List<Integer> attributesIDsList = new ArrayList<>();
    JSONArray allAtts;
    JSONArray allGroups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        attributesList.clear();
        attributesIDs.clear();
        registryAttributesIDList.clear();

        getAllAttributes();
        getAllAttributesGroups();

        Intent intent = getIntent();
        final int registryID = intent.getIntExtra("registryID",0);
        String allRegAttS = intent.getStringExtra("allRegAtt");
        String allAttributesS = intent.getStringExtra("allGroups");
        String allAttributesGroupsS = intent.getStringExtra("allAttributesGroups");
        setContentView(R.layout.activity_list_attributes);
        getSupportActionBar().setTitle(getResources().getString(R.string.list_attributes));
        recyclerView = (RecyclerView) findViewById(R.id.attributes_recycler_view);

        try {
            JSONArray allRegAtt = new JSONArray(allRegAttS);
            JSONArray attributes = new JSONArray(allAttributesS);

            for (int i = 0; i < allRegAtt.length(); i++) {
                if (allRegAtt.getJSONObject(i).getString("registry_id").equals(String.valueOf(registryID))) {
                    attributesIDs.add(Integer.parseInt(allRegAtt.getJSONObject(i).getString("attribute_id")));
                }
            }

            for (int i = 0; i < attributes.length(); i++) {
                for (int j = 0; j < attributesIDs.size(); j++) {
                    if (attributes.getJSONObject(i).getString("id").equals(String.valueOf(attributesIDs.get(j)))) {
                        for(int k=0;k<allRegAtt.length();k++){
                            if(allRegAtt.getJSONObject(k).getString("registry_id").equals(String.valueOf(registryID)) && allRegAtt.getJSONObject(k).getString("attribute_id").equals(attributes.getJSONObject(i).getString("id"))){
                                Attribute att = new Attribute(attributes.getJSONObject(i).getString("name"),allRegAtt.getJSONObject(k).getString("value"),Integer.parseInt(attributes.getJSONObject(i).getString("id")));
                                attributesList.add(att);
                                registryAttributesIDList.add(Integer.parseInt(allRegAtt.getJSONObject(k).getString("id")));
                            }
                        }

                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new AttributesAdapter(attributesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                        final Attribute attTMP = attributesList.get(position);
                        final int regAttID = registryAttributesIDList.get(position);
                        System.out.println(regAttID);
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Intent intent = new Intent(ListAttributesActivity.this, EditAttributeActivity.class);
                                intent.putExtra("name", attTMP.getName());
                                intent.putExtra("value",attTMP.getType());
                                intent.putExtra("id",regAttID);
                                intent.putExtra("attID",attTMP.getID());
                                intent.putExtra("registryID",registryID);
                                startActivity(intent);
                            }
                        };
                        GetRegistryAttributesRequest request = new GetRegistryAttributesRequest(regAttID,responseListener, TokenSaver.getToken(ListAttributesActivity.this));
                        RequestQueue queue = Volley.newRequestQueue(ListAttributesActivity.this);
                        queue.add(request);
                    }
                })
        );

        menu1 = (FloatingActionButton)findViewById(R.id.subFloatingMenu1) ;
        menu2 = (FloatingActionButton)findViewById(R.id.subFloatingMenu2) ;


        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListAttributesActivity.this, AddAttributeActivity.class);
                intent.putExtra("registryID",registryID);
                intent.putExtra("allAtts",allAtts.toString());
                startActivity(intent);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListAttributesActivity.this, AddManyAttributesActivity.class);
                intent.putExtra("registryID",registryID);
                intent.putExtra("allGroups",allGroups.toString());
                startActivity(intent);
            }
        });
    }

    private void prepareData(){
        attributesList.clear();
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
        GetAllAttributesRequest request1 = new GetAllAttributesRequest (responseListener,getToken(ListAttributesActivity.this));
        RequestQueue queue1 = Volley.newRequestQueue(ListAttributesActivity.this);
        queue1.add(request1);
    }

    private void getAllAttributesGroups(){
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
        GetAllAttributesGroupsRequest request1 = new GetAllAttributesGroupsRequest (responseListener,getToken(ListAttributesActivity.this));
        RequestQueue queue1 = Volley.newRequestQueue(ListAttributesActivity.this);
        queue1.add(request1);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
