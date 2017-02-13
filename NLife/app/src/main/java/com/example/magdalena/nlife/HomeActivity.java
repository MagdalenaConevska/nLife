package com.example.magdalena.nlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.GridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.View.inflate;


public class HomeActivity extends MasterActivity {

    Product product;
    Boolean lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("HomeActivity", "before addItem in onCreate()");
        addItem();
        Log.d("HomeActivity", "after addItem in onCreate()");

        GridView gridView=(GridView)this.findViewById(R.id.gridView);
        GridViewAdapter gridViewAdapter=new GridViewAdapter(getApplicationContext());
        if(lista != null){
            Log.d("HomeActivity", product.toString());
            gridViewAdapter.setProduct(product, lista);
        }

        Log.d("HomeActivity", "setLista");

        gridView.setAdapter(gridViewAdapter);



      //  Log.d("home","created");
       // new getDataFromSQLite(getApplicationContext(),"Monday").execute();


    }

    @Override
    protected void onResume() {
        super.onResume();
       /* Log.d("HomeActivity", "before addItem in onResume()");
        addItem();
        Log.d("HomeActivity", "after addItem in onResume()");

        */
    }

    public void addItem(){

        Log.d("HomeActivity", "onResume");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            Log.d("HomeActivity", "Bundle isn't null");
            Product p = new Product(bundle.getString("name"), Integer.parseInt(bundle.getString("id")), Integer.parseInt(bundle.getString("amount")));
            Log.d("HomeActivity", bundle.getString("name") + " " + Integer.parseInt(bundle.getString("amount")) + " g " + Integer.parseInt(bundle.getString("id")) + " ");

            Log.d("HomeActivity", bundle.getString("day"));

            SharedPreferences sp = getApplicationContext().getSharedPreferences("history", getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            String day = sp.getString("day", null);

            if(day != null){
                Log.d("HomeActivity", "day");
                Log.d("HomeActivity", day);
                if(day.compareTo(bundle.getString("day")) == 0){
                    Log.d("HomeActivity", "Same day");
                    product = p;
                    lista = true;
                } else {
                    Log.d("HomeActivity", "different day");
                    lista = false;
                    day = bundle.getString("day");
                    editor.putString("day", day);
                    editor.commit();
                    product = p;
                }
            } else {
                Log.d("HomeActivity", "day was null");
                lista = false;
                day = bundle.getString("day");
                editor.putString("day", day);
                editor.commit();
                Log.d("HomeActivity", day);
                Log.d("HomeActivity", sp.getString("day", null));
                product = p;
            }

        }
    }


    }

