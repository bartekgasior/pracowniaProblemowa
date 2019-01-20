package com.example.dpiotr.projekt.AttributesGroups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.Attributes.AddAttributeActivity;
import com.example.dpiotr.projekt.Attributes.AddAttributeRequest;
import com.example.dpiotr.projekt.Attributes.ListAttributesActivity;
import com.example.dpiotr.projekt.R;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class AddGroupActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_actvity);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final Button bDodaj = (Button) findViewById(R.id.bAdd);

        bDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(AddGroupActvity.this, ListGroups.class);
                        AddGroupActvity.this.startActivity(intent);
                    }
                };

                AddGroupRequest request = new AddGroupRequest(name, responseListener, getToken(AddGroupActvity.this));
                RequestQueue queue = Volley.newRequestQueue(AddGroupActvity.this);
                queue.add(request);
            }
        });
    }
}
