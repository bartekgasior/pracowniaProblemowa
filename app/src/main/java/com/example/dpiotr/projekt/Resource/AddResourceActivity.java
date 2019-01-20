package com.example.dpiotr.projekt.Resource;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.Registry.MainActivity;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.Registry.AddRegistryRequest;
import com.example.dpiotr.projekt.TokenSaver;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class AddResourceActivity extends AppCompatActivity {

    int selected_position_room;
    int selected_position_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource);

        getSupportActionBar().setTitle(getResources().getString(R.string.add_resource));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText name = (EditText) findViewById(R.id.editTextName);
        Spinner sRooms = (Spinner) findViewById(R.id.sRooms);
        Spinner sStates = (Spinner) findViewById(R.id.sStates);
        Button addBtn = (Button) findViewById(R.id.addBtn);

        Intent intent = getIntent();
        final String array = intent.getStringExtra("jsonArray");
        final String statesArray = intent.getStringExtra("statesArray");

        final ArrayList<String> rooms = new ArrayList<String>();
        final ArrayList<String> states = new ArrayList<String>();
        final ArrayList<String> room_number = new ArrayList<String>();
        final ArrayList<String> room_name = new ArrayList<String>();
        final ArrayList<Integer> roomsIDS = new ArrayList<Integer>();
        /*###############################################################################*/
        try {
            final JSONArray jsonArray = new JSONArray(array);
            for(int i=0;i<jsonArray.length();i++){
                room_name.add(jsonArray.getJSONObject(i).getString("name"));
                room_number.add(jsonArray.getJSONObject(i).getString("number"));
                String room = room_name.get(i) + " [" + room_number.get(i) + ']';
                rooms.add(room);
                roomsIDS.add(Integer.parseInt(jsonArray.getJSONObject(i).getString("id")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddResourceActivity.this,android.R.layout.simple_spinner_item, rooms);
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

        ArrayAdapter<String> statesAdapter = new ArrayAdapter<String>(AddResourceActivity.this,android.R.layout.simple_spinner_item, states);
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sStates.setAdapter(statesAdapter);

        sStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_position_state = position;
                Log.d("POSITION",states.get(selected_position_state));
                System.out.println(selected_position_state);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*###############################################################################*/

        addBtn.setOnClickListener(new View.OnClickListener() {
            final String nameTMP = name.getText().toString();
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(AddResourceActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                };
                AddRegistryRequest request = new AddRegistryRequest(name.getText().toString(), Integer.toString(TokenSaver.getID(getApplicationContext())), String.valueOf(roomsIDS.get(selected_position_room)),Integer.toString(selected_position_state), responseListener, getToken(getApplicationContext()));
                RequestQueue queue = Volley.newRequestQueue(AddResourceActivity.this);
                queue.add(request);
            }
        });

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