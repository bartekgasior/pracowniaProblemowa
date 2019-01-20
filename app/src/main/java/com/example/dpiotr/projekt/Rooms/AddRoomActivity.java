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
import com.example.dpiotr.projekt.R;

import org.json.JSONArray;
import org.json.JSONException;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class AddRoomActivity extends AppCompatActivity {

    boolean correct = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_room));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etNumber = (EditText) findViewById(R.id.textView4);
        final Button bAdd = (Button) findViewById(R.id.bAdd);

        Intent intent= getIntent();
        final String allRooms = intent.getStringExtra("roomsArray");
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct=true;
                final String name = etName.getText().toString();
                final String number = etNumber.getText().toString();

                try {
                    JSONArray jsonArrayAtts = new JSONArray(allRooms);
                    if(name.length()==0){
                        Toast toast = Toast.makeText(getApplicationContext(),"Wpisz nazwę.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        correct = false;
                    }
                    else if(number.length()==0){
                        Toast toast = Toast.makeText(getApplicationContext(),"Wpisz numer pokoju.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        correct = false;
                    }else if(name.length()>0 && number.length()>0){
                        for(int i=0;i<jsonArrayAtts.length();i++){
                            if(name.equalsIgnoreCase(jsonArrayAtts.getJSONObject(i).getString("name")) && number.equalsIgnoreCase(jsonArrayAtts.getJSONObject(i).getString("number"))){
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
                        Intent intent1 = new Intent(AddRoomActivity.this, UpdateListRoomsActivity.class);
                        AddRoomActivity.this.startActivity(intent1);
                    }
                };
                if(correct==true){
                    AddRoomRequest request = new AddRoomRequest(name, number, responseListener,getToken(AddRoomActivity.this));
                    RequestQueue queue = Volley.newRequestQueue(AddRoomActivity.this);
                    queue.add(request);
                }
            }
        }
        );
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
