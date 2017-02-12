package com.example.magdalena.nlife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.GridView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class HomeActivity extends MasterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        GridView gridView=(GridView)this.findViewById(R.id.gridView);
        GridViewAdapter gridViewAdapter=new GridViewAdapter(getApplicationContext());
        gridView.setAdapter(gridViewAdapter);



      //  Log.d("home","created");
       // new getDataFromSQLite(getApplicationContext(),"Monday").execute();


    }
}
