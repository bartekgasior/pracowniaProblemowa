package com.example.dpiotr.projekt.Rooms;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.ChooseOption.ChooseOptionActvity;
import com.example.dpiotr.projekt.DividerItemDecoration;

import com.example.dpiotr.projekt.Login.LoginActivity;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.RecyclerItemClickListener;
import com.example.dpiotr.projekt.Registry.GetAllRegistryRequest;
import com.example.dpiotr.projekt.Registry.MainActivity;
import com.example.dpiotr.projekt.TokenSaver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.dpiotr.projekt.TokenSaver.getToken;


public class ListRoomsActivity extends AppCompatActivity {
    private static List<Room> roomsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private static RoomsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_rooms);
        getSupportActionBar().setTitle(getResources().getString(R.string.rooms_list));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.rooms_recycler_view);

        mAdapter = new RoomsAdapter(roomsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        roomsList.clear();

        Intent intent = getIntent();
        String registryArray = intent.getStringExtra("registryArray");
        final String roomsArray = intent.getStringExtra("roomsArray");

        try{
            JSONArray registry = new JSONArray(registryArray);
            JSONArray rooms = new JSONArray(roomsArray);
            for (int i = 0; i < registry.length(); i++) {
                if(registry.getJSONObject(i).getString("user_id").equals(String.valueOf(TokenSaver.getID(getApplicationContext())))) {
                    JSONObject obj = registry.getJSONObject(i);
                    for(int j=0;j<rooms.length();j++){

                        if(obj.getString("room_id").equals(rooms.getJSONObject(j).getString("id"))){
                            Room newRoom = new Room(rooms.getJSONObject(j).getString("name"),rooms.getJSONObject(j).getString("number"), Integer.parseInt(rooms.getJSONObject(j).getString("id")));
                            System.out.println(newRoom.getName().toString());
                            roomsList.add(newRoom);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            for(int idx=0;idx<roomsList.size();idx++) {
                for(int idx1=0;idx1<roomsList.size();idx1++){
                    Room r = roomsList.get(idx);
                    Room r1 = roomsList.get(idx1);
                    if(idx!=idx1){
                        if (r.getName().toString().equals(r1.getName().toString()) && r.getNumber().toString().equals(r1.getNumber().toString())) {
                            roomsList.remove(idx1);
                            idx1--;
                        }

                    }
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }





        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                        final Room roomTMP = roomsList.get(position);
                        final int id=roomTMP.getID();
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Intent intent = new Intent(ListRoomsActivity.this, EditRoomActivity.class);
                                intent.putExtra("name",roomTMP.getName());
                                intent.putExtra("number",roomTMP.getNumber());
                                intent.putExtra("id",id);
                                intent.putExtra("roomsArray",roomsArray);
                                startActivity(intent);
                            }
                        };
                        GetRoomRequest request = new GetRoomRequest(id,responseListener,TokenSaver.getToken(getApplicationContext()));
                        RequestQueue queue = Volley.newRequestQueue(ListRoomsActivity.this);
                        queue.add(request);
                    }
                })
        );

        FloatingActionButton myFab = (FloatingActionButton)  findViewById(R.id.rooms_myFAB);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ListRoomsActivity.this, AddRoomActivity.class);
                myIntent.putExtra("roomsArray",roomsArray);
                startActivity(myIntent);
            }
        });
    }

    private void prepareResources() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        //prepareResources();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    onBackPressed();
                    return true;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(ListRoomsActivity.this, ChooseOptionActvity.class));
        finish();
    }

}
