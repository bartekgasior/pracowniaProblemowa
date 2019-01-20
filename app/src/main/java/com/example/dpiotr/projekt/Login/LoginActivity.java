package com.example.dpiotr.projekt.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.AttributesGroups.ListGroups;
import com.example.dpiotr.projekt.ChooseOption.ChooseOptionActvity;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.Register.RegisterActivity;
import com.example.dpiotr.projekt.RegistryAttributes.AddRegistryAttributesRequest;
import com.example.dpiotr.projekt.RegistryAttributes.GetAllRegistryAttributesRequest;
import com.example.dpiotr.projekt.Rooms.ListRoomsActivity;
import com.example.dpiotr.projekt.TokenSaver;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle(getResources().getString(R.string.login));

        final EditText etLogin = (EditText) findViewById(R.id.etLogin);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegister);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String login = etLogin.getText().toString();
                final String password = etPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(login.length()>0) {
                            if (password.length() > 0) {
                                try {
                                    Intent intent = new Intent(LoginActivity.this, ChooseOptionActvity.class);
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String token;
                                    if(jsonResponse.getString("state").equals("false")){
                                        Toast toast = Toast.makeText(getApplicationContext(),"Zły login lub hasło", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                    else {
                                        token = jsonResponse.getString("token");
                                        System.out.println("token - " + token);
                                        TokenSaver.setToken(getApplicationContext(), token);
                                        intent.putExtra("login", login);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                Toast toast = Toast.makeText(getApplicationContext(),"Wpisz hasło", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                        else{
                            Toast toast = Toast.makeText(getApplicationContext(),"Wpisz login", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(login,password,responseListener);
                RequestQueue queue1 = Volley.newRequestQueue(LoginActivity.this);
                queue1.add(loginRequest);
            }
        });


    }
}