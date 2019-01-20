package com.example.dpiotr.projekt.Registry;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.ChooseOption.GetAllStatesRequest;
import com.example.dpiotr.projekt.DividerItemDecoration;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.RecyclerItemClickListener;
import com.example.dpiotr.projekt.Resource.AddResourceActivity;
import com.example.dpiotr.projekt.Resource.Resource;
import com.example.dpiotr.projekt.Resource.ResourceAdapter;
import com.example.dpiotr.projekt.Rooms.EditRoomActivity;
import com.example.dpiotr.projekt.Rooms.GetAllRoomsRequest;
import com.example.dpiotr.projekt.TokenSaver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ListRegistryFromRooms extends AppCompatActivity {

    private static List<Resource> resourceList = new ArrayList<>();
    private RecyclerView recyclerView;
    private static ResourceAdapter mAdapter;

    JSONArray jsonArray;
    JSONArray roomsArray;
    List <Integer> registryIDsList = new ArrayList<Integer>();
    final ArrayList<String> states = new ArrayList<String>();
    final ArrayList<String> states1 = new ArrayList<String>();

    String roomName;
    String roomNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_registry_from_rooms);

        getSupportActionBar().setTitle(getResources().getString(R.string.room_resources));

        states1.add("Niedostępny");
        states1.add("Dostępny");
        states1.add("Zepsuty");
        states1.add("W serwisie");

        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomName");
        roomNo = intent.getStringExtra("roomNo");


        FloatingActionButton myFab = (FloatingActionButton)  findViewById(R.id.myFAB);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(ListRegistryFromRooms.this,AddResourceActivity.class);
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray array = jsonResponse.getJSONArray("result");
                            intent.putExtra("jsonArray", array.toString());
                            intent.putExtra("statesArray",jsonArray.toString());
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                    }
                };
                GetAllRoomsRequest request = new GetAllRoomsRequest(responseListener,TokenSaver.getToken(getApplicationContext()));
                RequestQueue queue1 = Volley.newRequestQueue(ListRegistryFromRooms.this);
                queue1.add(request);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ResourceAdapter(resourceList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);






        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view,final int position) {

                        final int id=registryIDsList.get(position);
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Intent intent = new Intent(ListRegistryFromRooms.this,EditActivity.class);

                                    JSONObject jsonResponse = new JSONObject(response);
                                    JSONObject obj = jsonResponse.getJSONObject("result");

                                    intent.putExtra("id",Integer.parseInt(obj.getString("id")));
                                    intent.putExtra("jsonObject", obj.toString());
                                    intent.putExtra("statesArray", jsonArray.toString());
                                    intent.putExtra("roomsArray", roomsArray.toString());
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        GetRegistryRequest request = new GetRegistryRequest(id,responseListener, TokenSaver.getToken(getApplicationContext()));
                        RequestQueue queue = Volley.newRequestQueue(ListRegistryFromRooms.this);
                        queue.add(request);
                    }
                })
        );

    }

    private void prepareResources(final int room_id) {
        resourceList.clear();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String jsonArray = jsonResponse.getJSONArray("result").toString();
                    JSONArray array = new JSONArray(jsonArray);

                    for (int i = 0; i < array.length(); i++) {
                        if(array.getJSONObject(i).getString("user_id").equals(String.valueOf(TokenSaver.getID(getApplicationContext()))) && array.getJSONObject(i).getString("room_id").equals(String.valueOf(room_id))) {
                            JSONObject obj = array.getJSONObject(i);
                            registryIDsList.add(Integer.parseInt(obj.getString("id")));
                            Resource res = new Resource(obj.getString("name"),roomName + "["+ roomNo +"]",obj.getString("user_id"),states1.get(Integer.parseInt(obj.getString("state"))));
                            resourceList.add(res);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        };
        GetAllRegistryRequest request = new GetAllRegistryRequest(responseListener, TokenSaver.getToken(getApplicationContext()));
        RequestQueue queue = Volley.newRequestQueue(ListRegistryFromRooms.this);
        queue.add(request);
    }

    public void getStates(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse1 = new JSONObject(response);
                    jsonArray = jsonResponse1.getJSONArray("result");
                    System.out.println("stany " + jsonArray.toString());
                    final JSONArray jsonArrayStates = new JSONArray(jsonArray.toString());
                    for(int i=0;i<jsonArrayStates.length();i++){
                        states.add(jsonArrayStates.getString(i));
                        Log.d("STANY",states.get(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetAllStatesRequest request1 = new GetAllStatesRequest(responseListener,TokenSaver.getToken(getApplicationContext()));
        RequestQueue queue1 = Volley.newRequestQueue(ListRegistryFromRooms.this);
        queue1.add(request1);
    }

    public void getRooms(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse1 = new JSONObject(response);
                    roomsArray = jsonResponse1.getJSONArray("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetAllRoomsRequest request1 = new GetAllRoomsRequest(responseListener,TokenSaver.getToken(getApplicationContext()));
        RequestQueue queue1 = Volley.newRequestQueue(ListRegistryFromRooms.this);
        queue1.add(request1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getStates();
        getRooms();
        Intent intent = getIntent();
        final int roomID=intent.getIntExtra("roomID",0);
        prepareResources(roomID);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
