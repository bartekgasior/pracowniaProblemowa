package com.example.dpiotr.projekt.Registry;

import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.DividerItemDecoration;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.RecyclerItemClickListener;
import com.example.dpiotr.projekt.Resource.AddResourceActivity;
import com.example.dpiotr.projekt.Resource.Resource;
import com.example.dpiotr.projekt.Resource.ResourceAdapter;
import com.example.dpiotr.projekt.Rooms.GetAllRoomsRequest;
import com.example.dpiotr.projekt.TokenSaver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<Resource> resourceList = new ArrayList<>();
    private RecyclerView recyclerView;
    private static ResourceAdapter mAdapter;
    String statesArray;
    String roomsArray;
    final ArrayList<String> rooms = new ArrayList<String>();
    final ArrayList<Integer> roomsIDS = new ArrayList<Integer>();
    final ArrayList<String> states = new ArrayList<String>();
    final ArrayList<String> room_number = new ArrayList<String>();
    final ArrayList<String> room_name = new ArrayList<String>();

    List <Integer> registryIDsList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(getResources().getString(R.string.resources_list));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        statesArray = intent.getStringExtra("statesArray");
        roomsArray = intent.getStringExtra("roomsArray");


        try {
            final JSONArray jsonArrayStates = new JSONArray(statesArray);
            for(int i=0;i<jsonArrayStates.length();i++){
                states.add(jsonArrayStates.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            final JSONArray jsonArray = new JSONArray(roomsArray);
            for(int i=0;i<jsonArray.length();i++){
                room_name.add(jsonArray.getJSONObject(i).getString("name"));
                room_number.add(jsonArray.getJSONObject(i).getString("number"));
                String actRoom = room_name.get(i) + " [" + room_number.get(i) + ']';
                rooms.add(actRoom);
                roomsIDS.add(Integer.parseInt(jsonArray.getJSONObject(i).getString("id")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FloatingActionButton myFab = (FloatingActionButton)  findViewById(R.id.myFAB);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(MainActivity.this,AddResourceActivity.class);
                        try{
                            JSONArray jsonArray = new JSONArray(statesArray);
                            JSONArray roomsArray = new JSONArray(MainActivity.this.roomsArray);
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
                RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
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
                                    JSONArray jsonArray = new JSONArray(statesArray);
                                    JSONArray roomsArray = new JSONArray(MainActivity.this.roomsArray);
                                    Intent intent = new Intent(MainActivity.this,EditActivity.class);

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
                        GetRegistryRequest request = new GetRegistryRequest(id,responseListener,TokenSaver.getToken(getApplicationContext()));
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        queue.add(request);
                    }
                })
        );

    }

    private void prepareResources() {
        resourceList.clear();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String jsonArray = jsonResponse.getJSONArray("result").toString();
                    JSONArray array = new JSONArray(jsonArray);

                    for (int i = 0; i < array.length(); i++) {
                        if(array.getJSONObject(i).getString("user_id").equals(String.valueOf(TokenSaver.getID(getApplicationContext())))) {
                            JSONObject obj = array.getJSONObject(i);
                            registryIDsList.add(Integer.parseInt(obj.getString("id")));
                            String roomNM="";
                            for(int j=0;j<roomsIDS.size();j++){
                                if(roomsIDS.get(j).equals(Integer.parseInt(obj.getString("room_id")))){
                                    roomNM=rooms.get(j);
                                }
                            }
                            Resource res = new Resource(obj.getString("name"),roomNM,
                                    obj.getString("user_id"),states.get(Integer.parseInt(obj.getString("state"))));
                            Log.d("STATES:",obj.getString("state"));
                            Log.d("NAMES:",roomNM);
                            resourceList.add(res);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            };
        GetAllRegistryRequest request = new GetAllRegistryRequest(responseListener,TokenSaver.getToken(getApplicationContext()));
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(request);
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    public static Resource getResource(int position) {
        return resourceList.get(position);
    }

    public static  void addResource(Resource resource){
        resourceList.add(resource);
        mAdapter.notifyDataSetChanged();
    }
    public static  void addResource(int position,Resource resource){
        resourceList.add(position,resource);
        mAdapter.notifyDataSetChanged();
    }

    public static void cleanResourceList(){
        resourceList.clear();
    }

    public static void deleteResource(int position){
        resourceList.remove(position);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();
        prepareResources();
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
        finish();
    }

}
