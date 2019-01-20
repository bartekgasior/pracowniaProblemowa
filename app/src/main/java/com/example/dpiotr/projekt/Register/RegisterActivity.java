package com.example.dpiotr.projekt.Register;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    boolean correct = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle(getResources().getString(R.string.register));

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etSurname = (EditText) findViewById(R.id.etSurname);
        final EditText etLogin = (EditText) findViewById(R.id.etLogin);
        final EditText etEMail = (EditText) findViewById(R.id.etEMail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etRePassword = (EditText) findViewById(R.id.etRePassword);
        final Button bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String surname = etSurname.getText().toString();
                final String email = etEMail.getText().toString();
                final String password = etPassword.getText().toString();
                final String rePassword = etRePassword.getText().toString();
                final String login = etLogin.getText().toString();

                final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                if (!validateName(name)){
                    builder.setMessage("Wprowadzono złe imię")
                            .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .create()
                            .show();
                }
                else if (!validateName(surname)){
                    builder.setMessage("Wprowadzono złe nazwisko")
                            .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .create()
                            .show();
                }
                else if(!validateLogin(login)){
                    builder.setMessage("Wprowadzono zły login")
                            .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .create()
                            .show();
                }
                else if(!validateEMail(email)){
                    builder.setMessage("Wprowadzono zły E-Mail")
                            .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .create()
                            .show();
                }
                else if(!validatePassword(password)){
                    builder.setMessage("Wprowadzono złe hasło")
                            .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .create()
                            .show();
                }
                else if(!password.equals(rePassword)){
                    builder.setMessage("Hasła nie są identyczne")
                            .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .create()
                            .show();
                }
                else{
                    correct = true;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            builder.setMessage("Zarejestrowano")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            RegisterActivity.this.startActivity(intent);
                                        }
                                    })
                                    .create()
                                    .show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                if(correct==true){
                    RegisterRequest registerRequest = new RegisterRequest(name, surname, login, email, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
            }
        });
    }

    protected boolean validateName(String name){
       // String REGEX="^[A-Z]+[a-z]";
        String REGEX="^([A-Z][a-z]*)";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    protected boolean validateLogin(String login){
        String REGEX = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    protected boolean validateEMail(String EMail){
        String REGEX="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(EMail);
        return matcher.matches();
    }

    protected boolean validatePassword(String password){
        String REGEX="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{3,20})"; // 1 - cyfra, mala litera, duza litera 3-20 znakow
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}