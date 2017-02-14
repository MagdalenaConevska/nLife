package com.example.magdalena.nlife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailsActivity extends MasterActivity {

    ArrayList<Nutrient> lista;
    String name;
    //ListView lv;
    String[] niza;
    ArrayAdapter<String> ad;
    ArrayList<Product> products;
    String id;
    String amount;
    String currentDayOfTheWeek;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("DetailsActivity", "1. Broadcast received");
            lista = (ArrayList<Nutrient>) intent.getExtras().get("Nutrients");
            Log.d("DetailsActivity", "2. Broadcast received");
            showInList();
        }
    };

    BroadcastReceiver broadcastReceiverInsert = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("DetailsActivity","broadcast received");
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
            builder.setTitle("Recipe added");
            builder.setMessage("Recipe has been added into your history");
            builder.setCancelable(false);
            builder.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplication(), HomeActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("amount", amount);
                            intent.putExtra("id", id);
                            intent.putExtra("day", currentDayOfTheWeek);
                            Log.d("DetailsActivity", name + " " + amount + " g " + id + " ");
                            startActivity(intent);
                        }
                    }
            );
            builder.show();
        }
    };

    private void showInList() {
        SharedPreferences sp = getSharedPreferences("ids", this.MODE_PRIVATE);
        name = sp.getString("name", null);
        id = sp.getString("id", null);
        //Log.d("IMEEEEEEEEEEEEEEEE",name);
        Log.d("Fragment", "got preference");
        TextView tv = (TextView) findViewById(R.id.productName);
        Log.d("Fragment", "got name");
        Log.d("DetailsActivity", "lista size " + lista.size());
        String[] parts = name.split(",");
        StringBuilder sb = new StringBuilder();
        tv.setText(parts[0]);
        Log.d("Fragment", "1");
        for (int i = 1; i < parts.length - 2; i++) {
            sb.append(parts[i]);
            sb.append(",");
        }
        Log.d("Fragment", "2");
        sb.append(parts[parts.length - 2]);
        TextView tvA = (TextView) findViewById(R.id.additionalInformation);
        tvA.setText(sb.toString());
        Log.d("Fragment", "3");
        niza = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            niza[i] = lista.get(i).toString();
            Log.d("DetailsActivity", niza[i]);
        }
        Log.d("DetailsActivity", "niza size " + niza.length);
        ListView lv = (ListView) findViewById(R.id.lvNutrients);
        ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, niza);
        Log.d("Fragment", "4");
        // int h = niza.length * 60;
        //lv.setMinimumHeight(h);
        //View v = findViewById(R.id.activity_search);
        //v.setMinimumHeight(450 + h);
        lv.setAdapter(ad);
        //View v = findViewById(R.id.detailsAdditional);
        //lv.addHeaderView(v);
        //lv.invalidate();
        Log.d("Fragment", "Broadcast registered");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        lista = new ArrayList<>();
        new GetReport(this).execute();
        SharedPreferences sp = getSharedPreferences("ids", this.MODE_PRIVATE);
        name = sp.getString("name", null);
        id = sp.getString("id", null);
        Button btn = (Button) findViewById(R.id.btnNutrients);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DetailsActivity", "ButtonClicked");
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                Date now = new Date();
                currentDayOfTheWeek = sdf.format(now);
                Log.d("DetailsActivity", currentDayOfTheWeek);
                try {
                    amount = ((EditText) findViewById(R.id.numberPicker)).getText().toString();
                    Log.d("DetailsActivity", amount);
                    int kolichina = Integer.parseInt(amount);
                    Product p = new Product(name, Integer.parseInt(id), kolichina);
                    //da se proveri dali raboti zapisot vo baza!!!
                    new InsertIntoDataBase(getApplicationContext(), name, currentDayOfTheWeek, id, kolichina, lista).execute();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Please enter amount.", Toast.LENGTH_LONG);
                    Log.e("DetailsActivity", e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("GetReportNutrients");
        registerReceiver(broadcastReceiver, filter);
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("InsertToSQLite");
        registerReceiver(broadcastReceiverInsert, filter2);
        Log.d("DetailsActivity","broadcast registered");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiverInsert);
        Log.d("Fragment", "Broadcast unregistered");
    }
}



