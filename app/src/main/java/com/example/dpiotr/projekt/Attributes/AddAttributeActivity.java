package com.example.dpiotr.projekt.Attributes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dpiotr.projekt.AttributesGroups.GetAllAttributesGroupsRequest;
import com.example.dpiotr.projekt.Login.LoginActivity;
import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.Registry.EditActivity;
import com.example.dpiotr.projekt.RegistryAttributes.AddRegistryAttributesRequest;
import com.example.dpiotr.projekt.RegistryAttributes.GetAllRegistryAttributesRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

public class AddAttributeActivity extends AppCompatActivity {

    JSONArray attributes;
    JSONArray allRegAtt;
    JSONArray allAttibutes;
    JSONArray allAttributesGroups;
    int selected_position_att;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attribute);

        getAllAttributes();

        final EditText etType = (EditText) findViewById(R.id.etWartosc);
        final Button bDodaj = (Button) findViewById(R.id.bDodaj);
        final Spinner sAttributes = (Spinner) findViewById(R.id.sAttributes);
        final TextView addNewActivityLink = (TextView) findViewById(R.id.tvAddNewActivity);
        Intent intent = getIntent();
        final int registryID = intent.getIntExtra("registryID",0);
        String atts = intent.getStringExtra("allAtts");


        final ArrayList<String> attsArray = new ArrayList<String>();
        final ArrayList<Integer> attsIDsArray = new ArrayList<Integer>();

        try {
            JSONArray jsonArrayAtts = new JSONArray(atts);
            for(int i=0;i<jsonArrayAtts.length();i++){
                attsArray.add(jsonArrayAtts.getJSONObject(i).getString("name"));
                attsIDsArray.add(Integer.parseInt(jsonArrayAtts.getJSONObject(i).getString("id")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddAttributeActivity.this,android.R.layout.simple_spinner_item, attsArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sAttributes.setAdapter(adapter);

        sAttributes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_position_att = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String value = etType.getText().toString();
                addRegistryAttribute(attsIDsArray.get(selected_position_att),registryID, value);
                System.out.println(allRegAtt);
                Intent intent = new Intent(AddAttributeActivity.this, UpdateAttributesListActivity.class);
                intent.putExtra("registryID", registryID);
                AddAttributeActivity.this.startActivity(intent);
            }
        });

        addNewActivityLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAttributeActivity.this, AddNewAttributeActivity.class);
                intent.putExtra("registryID", registryID);
                intent.putExtra("allAttributes", allAttibutes.toString());
                AddAttributeActivity.this.startActivity(intent);
            }
        });
    }

    public void addRegistryAttribute(int att_id, int reg_id, String value){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        };
        AddRegistryAttributesRequest request = new AddRegistryAttributesRequest( reg_id, att_id, value, responseListener,getToken(AddAttributeActivity.this));
        RequestQueue queue = Volley.newRequestQueue(AddAttributeActivity.this);
        queue.add(request);
    }

    public void getAllAttributes(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    allAttibutes = jsonResponse.getJSONArray("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetAllAttributesRequest request1 = new GetAllAttributesRequest (responseListener,getToken(AddAttributeActivity.this));
        RequestQueue queue1 = Volley.newRequestQueue(AddAttributeActivity.this);
        queue1.add(request1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
