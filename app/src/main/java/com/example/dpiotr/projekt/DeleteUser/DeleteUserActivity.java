package com.example.dpiotr.projekt.DeleteUser;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.Login.LoginActivity;
import com.example.dpiotr.projekt.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DeleteUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        final EditText etId = (EditText) findViewById(R.id.etId);
        final Button bDelete = (Button) findViewById(R.id.bDelete);

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = Integer.parseInt(etId.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Intent intent = new Intent(DeleteUserActivity.this, LoginActivity.class);
                            DeleteUserActivity.this.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DeleteUserRequest deleteRequest = new DeleteUserRequest(id,responseListener);
                RequestQueue queue = Volley.newRequestQueue(DeleteUserActivity.this);
                queue.add(deleteRequest);
            }
        });
    }
}