package com.example.magdalena.nlife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.GridView;


public class HomeActivity extends MasterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        GridView gridView=(GridView)this.findViewById(R.id.gridView);
        GridViewAdapter gridViewAdapter=new GridViewAdapter(getApplicationContext());
        gridView.setAdapter(gridViewAdapter);


        new getDataFromSQLite(getApplicationContext()).execute();


    }
}
