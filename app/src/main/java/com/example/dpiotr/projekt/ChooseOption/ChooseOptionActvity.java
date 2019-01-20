package com.example.dpiotr.projekt.ChooseOption;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.Login.GetAllUsersRequest;
import com.example.dpiotr.projekt.Login.LoginActivity;
import com.example.dpiotr.projekt.Registry.MainActivity;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.Registry.GetAllRegistryRequest;
import com.example.dpiotr.projekt.Rooms.GetAllRoomsRequest;
import com.example.dpiotr.projekt.Rooms.ListRoomsActivity;
import com.example.dpiotr.projekt.TokenSaver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChooseOptionActvity extends AppCompatActivity {
    int pos;
    JSONArray registry;
    JSONArray rooms;
    JSONArray states;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_option);

        getSupportActionBar().setTitle(getResources().getString(R.string.choose_option));

        final ImageButton bRooms = (ImageButton) findViewById(R.id.bRooms);
        final ImageButton bResources = (ImageButton) findViewById(R.id.bResources);


        Intent intentTMP = getIntent();
        final String login = intentTMP.getStringExtra("login");
        Log.d("LOGIN",intentTMP.getStringExtra("login"));
        TokenSaver.setLogin(getApplicationContext(),login);
        getId(TokenSaver.getLogin(getApplicationContext()));


        bRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokenSaver.setID(ChooseOptionActvity.this,pos);
                Intent intent = new Intent(ChooseOptionActvity.this, ListRoomsActivity.class);
                intent.putExtra("registryArray",registry.toString());
                System.out.println(registry.length());
                System.out.println(rooms.length());
                intent.putExtra("roomsArray",rooms.toString());
                startActivity(intent);
            }
        });

        bResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokenSaver.setID(ChooseOptionActvity.this,pos);
                Intent intent1 = new Intent(ChooseOptionActvity.this, MainActivity.class);
                intent1.putExtra("statesArray",states.toString());
                intent1.putExtra("roomsArray",rooms.toString());
                startActivity(intent1);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("GET STATES","GET RES");
        getRegistry();
        getStates();
        getRooms();
    }

    public void getId(final String loginTMP) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray array = jsonResponse.getJSONArray("result");
                    System.out.println(array.toString());
                    for(int i=0;i<array.length();i++){
                        if(loginTMP.equals(array.getJSONObject(i).getString("login"))){
                            pos=Integer.parseInt(array.getJSONObject(i).getString("id"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetAllUsersRequest request = new GetAllUsersRequest(responseListener,TokenSaver.getToken(getApplicationContext()));
        RequestQueue queue = Volley.newRequestQueue(ChooseOptionActvity.this);
        queue.add(request);
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
        GetAllRegistryRequest request = new GetAllRegistryRequest(responseListener,TokenSaver.getToken(getApplicationContext()));
        RequestQueue queue = Volley.newRequestQueue(ChooseOptionActvity.this);
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
        GetAllRoomsRequest request = new GetAllRoomsRequest(responseListener,TokenSaver.getToken(getApplicationContext()));
        RequestQueue queue = Volley.newRequestQueue(ChooseOptionActvity.this);
        queue.add(request);
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
        RequestQueue queue1 = Volley.newRequestQueue(ChooseOptionActvity.this);
        queue1.add(request1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Wylogować?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }
}
