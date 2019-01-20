package com.example.dpiotr.projekt.Rooms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.ChooseOption.ChooseOptionActvity;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.Registry.GetAllRegistryRequest;
import com.example.dpiotr.projekt.Registry.ListRegistryFromRooms;
import com.example.dpiotr.projekt.TokenSaver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class EditRoomActivity extends AppCompatActivity {
    JSONArray registry;
    JSONArray rooms;
    boolean correct=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_room);
        getSupportActionBar().setTitle(getResources().getString(R.string.edit_room));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getRooms();
        getRegistry();

        final EditText name = (EditText) findViewById(R.id.etName);
        final EditText number = (EditText) findViewById(R.id.etNumber);
        final Button bEdit = (Button) findViewById(R.id.bEdytuj);
        final Button bZasoby = (Button) findViewById(R.id.bZasoby);

        Intent intent = getIntent();
        final int roomID=intent.getIntExtra("id",0);
        final String allRooms = intent.getStringExtra("roomsArray");
        final String roomName = intent.getStringExtra("name");
        name.setText(roomName);
        final String roomNo = intent.getStringExtra("number");
        number.setText(roomNo);

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct=true;
                final String nameTMP = name.getText().toString();
                final String numberTMP = number.getText().toString();

                try {
                    JSONArray jsonArrayAtts = new JSONArray(allRooms);
                    if(nameTMP.length()==0){
                        Toast toast = Toast.makeText(getApplicationContext(),"Wpisz nazwę.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        correct = false;
                    }
                    else if(numberTMP.length()==0){
                        Toast toast = Toast.makeText(getApplicationContext(),"Wpisz numer pokoju.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        correct = false;
                    }
                    else if(nameTMP.length()>0 && numberTMP.length()>0){
                        for(int i=0;i<jsonArrayAtts.length();i++){
                            if(nameTMP.equalsIgnoreCase(jsonArrayAtts.getJSONObject(i).getString("name")) && numberTMP.equalsIgnoreCase(jsonArrayAtts.getJSONObject(i).getString("number"))){
                                Toast toast = Toast.makeText(getApplicationContext(),"Pokój o podanej nazwie oraz numerze znajduje się już w bazie danych.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                correct = false;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Intent intent = new Intent(EditRoomActivity.this, UpdateListRoomsActivity.class);
                            startActivity(intent);
                    }
                };
                if(correct==true) {
                    EditRoomRequest request = new EditRoomRequest(nameTMP, numberTMP, roomID, responseListener, TokenSaver.getToken(EditRoomActivity.this));
                    RequestQueue queue = Volley.newRequestQueue(EditRoomActivity.this);
                    queue.add(request);
                }
            }
        });

        bZasoby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditRoomActivity.this, ListRegistryFromRooms.class);
                intent.putExtra("roomID",roomID);
                intent.putExtra("roomNo",roomNo);
                intent.putExtra("roomName",roomName);
                startActivity(intent);
            };
        });
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
        GetAllRoomsRequest request = new GetAllRoomsRequest(responseListener,getToken(EditRoomActivity.this));
        RequestQueue queue = Volley.newRequestQueue(EditRoomActivity.this);
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
        GetAllRegistryRequest request = new GetAllRegistryRequest(responseListener,getToken(EditRoomActivity.this));
        RequestQueue queue = Volley.newRequestQueue(EditRoomActivity.this);
        queue.add(request);
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
