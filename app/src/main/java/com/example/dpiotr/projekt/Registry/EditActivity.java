package com.example.dpiotr.projekt.Registry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.Attributes.GetAllAttributesRequest;
import com.example.dpiotr.projekt.Attributes.ListAttributesActivity;
import com.example.dpiotr.projekt.AttributesGroups.GetAllAttributesGroupsRequest;
import com.example.dpiotr.projekt.AttributesGroups.ListGroups;
import com.example.dpiotr.projekt.ChooseOption.ChooseOptionActvity;
import com.example.dpiotr.projekt.ChooseOption.GetAllStatesRequest;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.RegistryAttributes.GetAllRegistryAttributesRequest;
import com.example.dpiotr.projekt.Rooms.GetAllRoomsRequest;
import com.example.dpiotr.projekt.TokenSaver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class EditActivity extends AppCompatActivity {

    public static final String RES_NO="resNo";
    private int resNo;
    int selected_position_state;
    int selected_position_room;

    JSONArray allRegAtt;
    JSONArray allGroups;
    JSONArray allAttributesGroups;
    JSONArray states;
    JSONArray rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getAllRegistryAttributes();
        getAllAttributes();
        getAllAttributesGroups();
        getStates();
        getSupportActionBar().setTitle(getResources().getString(R.string.edit_resource));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        final EditText name = (EditText) findViewById(R.id.edit_editTextName);
        final Spinner sState = (Spinner) findViewById(R.id.sState);
        final Spinner sRooms = (Spinner) findViewById(R.id.sRooms);
       // final EditText value = (EditText) findViewById(R.id.edit_editTextValue);

        Button editBtn = (Button) findViewById(R.id.edit_editBtn);
        Button bZasoby = (Button) findViewById(R.id.bZasoby);
        Button bDelete = (Button) findViewById(R.id.bDelete);
        final Intent intent = getIntent();
        String obj = intent.getStringExtra("jsonObject");
        String statesArray = intent.getStringExtra("statesArray");
        String roomsArray = intent.getStringExtra("roomsArray");

        final ArrayList<String> rooms = new ArrayList<String>();
        final ArrayList<Integer> roomsIDS = new ArrayList<Integer>();
        final ArrayList<String> states = new ArrayList<String>();
        final ArrayList<String> room_number = new ArrayList<String>();
        final ArrayList<String> room_name = new ArrayList<String>();

        final int id = intent.getIntExtra("id",0);

        /*###############################################################################*/

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditActivity.this,android.R.layout.simple_spinner_item, rooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sRooms.setAdapter(adapter);

        sRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_position_room = position;
                System.out.println(selected_position_room);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*###############################################################################*/

        try {
            final JSONArray jsonArrayStates = new JSONArray(statesArray);
            for(int i=0;i<jsonArrayStates.length();i++){
                states.add(jsonArrayStates.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> statesAdapter = new ArrayAdapter<String>(EditActivity.this,android.R.layout.simple_spinner_item, states);
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sState.setAdapter(statesAdapter);

        sState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_position_state = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*###############################################################################*/


        try {
            JSONObject jsonObj = new JSONObject(obj);
            name.setText(jsonObj.getString("name"));
            sState.setSelection(Integer.parseInt(jsonObj.getString("state")));

            JSONArray jsonArrayRooms = new JSONArray(roomsArray);
            for(int i=0;i<jsonArrayRooms.length();i++){
                if(jsonArrayRooms.getJSONObject(i).getString("id").equals(jsonObj.getString("room_id"))){
                    sRooms.setSelection(i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameTMP = name.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (name.length() > 0 ) {
                            Intent intent1 = new Intent(EditActivity.this, MainActivity.class);
                            intent1.putExtra("statesArray",states.toString());
                            intent1.putExtra("roomsArray",rooms.toString());
                            startActivity(intent1);
                        } else {
                            Toast.makeText(getApplicationContext(), "Wprowadź dane", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                EditRegistryRequest request = new EditRegistryRequest(nameTMP, Integer.toString(TokenSaver.getID(EditActivity.this)), String.valueOf(roomsIDS.get(selected_position_room)), String.valueOf(selected_position_state), id, responseListener,TokenSaver.getToken(EditActivity.this));
                RequestQueue queue = Volley.newRequestQueue(EditActivity.this);
                queue.add(request);
            }
        });

        bZasoby.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent1 = new Intent(EditActivity.this, ListAttributesActivity.class);
                intent1.putExtra("registryID",id);
                intent1.putExtra("allRegAtt", allRegAtt.toString());
                intent1.putExtra("allGroups", allGroups.toString());
                intent1.putExtra("allAttributesGroups", allAttributesGroups.toString());
                startActivity(intent1);
            }
        }
        );

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final String URL = "http://damian.dvtr.pl/ewidencja/public/api/registry/delete/";
                final String URL_token = "?token=";
                StringRequest dr = new StringRequest(Request.Method.DELETE, URL + Integer.toString(id) + URL_token + TokenSaver.getToken(EditActivity.this),
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Toast.makeText(EditActivity.this, response, Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error.
                                System.out.println("error" + error);
                                NetworkResponse networkResponse = error.networkResponse;
                                System.out.println(networkResponse);
                            }
                        }
                );
                RequestQueue queue1 = Volley.newRequestQueue(EditActivity.this);
                queue1.add(dr);
                /*Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response" + response);
                        Intent intent1 = new Intent(EditActivity.this, UpdateMainActivity.class);
                        startActivity(intent1);
                    }
                };
                Response.ErrorListener listener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error" + error);
                    }
                };
                DeleteRegistryRequest request1 = new DeleteRegistryRequest(id, responseListener,TokenSaver.getToken(EditActivity.this));
                RequestQueue queue1 = Volley.newRequestQueue(EditActivity.this);
                queue1.add(request1);*/
            }
        }
        );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(EditActivity.this, MainActivity.class);
        //myIntent.putExtra(ResourceActivity.RES_NO,resNo);
        startActivity(myIntent);
        return true;
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditActivity.this,MainActivity.class));
        finish();
    }*/

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
        RequestQueue queue = Volley.newRequestQueue(EditActivity.this);
        queue.add(request);
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
        GetAllRegistryAttributesRequest request1 = new GetAllRegistryAttributesRequest(responseListener,getToken(EditActivity.this));
        RequestQueue queue1 = Volley.newRequestQueue(EditActivity.this);
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
        GetAllAttributesRequest request1 = new GetAllAttributesRequest (responseListener,getToken(EditActivity.this));
        RequestQueue queue1 = Volley.newRequestQueue(EditActivity.this);
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
        GetAllAttributesGroupsRequest request1 = new GetAllAttributesGroupsRequest (responseListener,getToken(EditActivity.this));
        RequestQueue queue1 = Volley.newRequestQueue(EditActivity.this);
        queue1.add(request1);
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
        RequestQueue queue1 = Volley.newRequestQueue(EditActivity.this);
        queue1.add(request1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
