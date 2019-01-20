package com.example.dpiotr.projekt.Attributes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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

public class AddNewAttributeActivity extends AppCompatActivity {
    boolean correct = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_attribute);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final Button bAdd= (Button) findViewById(R.id.bAdd);

        Intent intent = getIntent();
        final int registryID = intent.getIntExtra("registryID",0);
        final String allAttributes = intent.getStringExtra("allAttributes");

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                correct=true;
                try {
                    JSONArray jsonArrayAtts = new JSONArray(allAttributes);
                    if(name.length()==0){
                        Toast toast = Toast.makeText(getApplicationContext(),"Wpisz nazwę.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        correct = false;
                    }else if(name.length()>0) {
                        for (int i = 0; i < jsonArrayAtts.length(); i++) {
                            if (name.equals(jsonArrayAtts.getJSONObject(i).getString("name"))) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Atrybut o podanej nazwie znajduje się już w bazie danych.", Toast.LENGTH_LONG);
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
                        Intent intent = new Intent(AddNewAttributeActivity.this, UpdateAttributesSpinnerActivity.class);
                        intent.putExtra("registryID",registryID);
                        AddNewAttributeActivity.this.startActivity(intent);
                    }
                };
                if(correct==true){
                    AddAttributeRequest request = new AddAttributeRequest(name,responseListener,getToken(AddNewAttributeActivity.this));
                    RequestQueue queue = Volley.newRequestQueue(AddNewAttributeActivity.this);
                    queue.add(request);
                }
            }
        });
    }

}
