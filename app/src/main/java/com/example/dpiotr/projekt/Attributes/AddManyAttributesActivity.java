package com.example.dpiotr.projekt.Attributes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.dpiotr.projekt.R;
import com.example.dpiotr.projekt.Registry.EditActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AddManyAttributesActivity extends AppCompatActivity {

    int selected_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_many_attributes);
        final Spinner sGroup = (Spinner) findViewById(R.id.sGroup);
        Button bAdd = (Button) findViewById(R.id.bAdd);

        Intent intent = getIntent();
        String allGroups = intent.getStringExtra("allGroups");
        int registryID=intent.getIntExtra("registryID",0);

        final ArrayList<String> groups = new ArrayList<String>();

        try {
            final JSONArray jsonArray = new JSONArray(allGroups);
            for(int i=0;i<jsonArray.length();i++){
                    groups.add(jsonArray.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddManyAttributesActivity.this,android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sGroup.setAdapter(adapter);

        sGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_position = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
