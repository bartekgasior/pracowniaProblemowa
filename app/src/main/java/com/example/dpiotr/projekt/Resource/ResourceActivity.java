package com.example.dpiotr.projekt.Resource;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dpiotr.projekt.Registry.EditActivity;
import com.example.dpiotr.projekt.Registry.MainActivity;
import com.example.dpiotr.projekt.R;

public class ResourceActivity extends AppCompatActivity {


    public static final String RES_NO="resNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        final int resNo = (Integer) getIntent().getExtras().get(RES_NO);
        Resource res = MainActivity.getResource(resNo);

        getSupportActionBar().setTitle(res.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        TextView nameTextView = (TextView) findViewById(R.id.res_activity_name);
        //TextView ownerTextView = (TextView) findViewById(R.id.res_activity_owner);
        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        Button editBtn = (Button) findViewById(R.id.editBtn);


        nameTextView.setText(res.getName());
        //ownerTextView.setText(res.getOwner());

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MainActivity.deleteResource(resNo);
                Intent myIntent = new Intent(ResourceActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra(ResourceActivity.RES_NO,resNo);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //MainActivity.cleanResourceList();
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
