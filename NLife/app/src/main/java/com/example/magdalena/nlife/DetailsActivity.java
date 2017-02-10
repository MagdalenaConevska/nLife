package com.example.magdalena.nlife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    ArrayList<Nutrient> lista;
    String name;
    //ListView lv;
    String[] niza;
    ArrayAdapter<String> ad;



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("DetailsActivity","1. Broadcast received");
            lista= (ArrayList<Nutrient>)intent.getExtras().get("Nutrients");
            Log.d("DetailsActivity","2. Broadcast received");

            showInList();

        }
    };

    private void showInList() {

        SharedPreferences sp = getSharedPreferences("ids", this.MODE_PRIVATE);
        name = sp.getString("name", null);
        Log.d("Fragment","got preference");
        TextView tv = (TextView)findViewById(R.id.productName);
        Log.d("Fragment","got name");
        Log.d("DetailsActivity","lista size " + lista.size());
        String []parts = name.split(",");
        StringBuilder sb = new StringBuilder();
        tv.setText(parts[0]);

        Log.d("Fragment","1");

        for(int i =1; i<parts.length-2;i++){
            sb.append(parts[i]);
            sb.append(",");
        }

        Log.d("Fragment","2");
        sb.append(parts[parts.length-2]);
        TextView tvA = (TextView)findViewById(R.id.additionalInformation);
        tvA.setText(sb.toString());
        Log.d("Fragment","3");

        niza = new String[lista.size()];

        for(int i=0; i<lista.size(); i++){
            niza[i] = lista.get(i).toString();
            Log.d("DetailsActivity", niza[i]);
        }

        Log.d("DetailsActivity","niza size " + niza.length);

        ListView lv = (ListView)findViewById(R.id.lvNutrients);
        ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, niza);

        Log.d("Fragment","4");

        lv.setAdapter(ad);
        //lv.invalidate();
        Log.d("Fragment","Broadcast registered");


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        lista = new ArrayList<>();
        Log.d("Fragment","onCreate");
        new GetReport(this).execute();
        Log.d("Fragment","executed");

        Button btn = (Button)findViewById(R.id.btnNutrients);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "apple";
                String datum = "09.02.2017";
                String ndbno = "01009";
                int kolichina = 2;


                double protein = 0, lipid = 0, carbo = 0, glucose = 0, calcium = 0;
                double iron = 0, mg = 0, zinc = 0, vitC = 0, thiamin = 0, ribo = 0;
                double niacin = 0, vitB6 = 0, vitB12 = 0, vitA = 0, vitD = 0, vitE = 0;


                for (int i = 0; i < lista.size(); i++) {

                    if (lista.get(i).name.equals("Protein")) {

                        protein = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Total lipid (fat)")) {

                        lipid = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Carbohydrate, by difference")) {

                        carbo = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Glucose (dextrose)")) {

                        glucose = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Calcium, Ca")) {

                        calcium = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Iron, Fe")) {

                        iron = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Magnesium, Mg")) {

                        mg = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Zinc, Zn")) {

                        zinc = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Vitamin C, total ascorbic acid")) {

                        vitC = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Thiamin")) {

                        thiamin = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Riboflavin")) {

                        ribo = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Niacin")) {

                        niacin = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Vitamin B-6")) {

                        vitB6 = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Vitamin B-12")) {
                        vitB12 = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Vitamin A, RAE")) {

                        vitA = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Vitamin D")) {

                        vitD = lista.get(i).recorded;
                    } else if (lista.get(i).name.equals("Vitamin E (alpha-tocopherol)")) {

                        vitE = lista.get(i).recorded;
                    }

                }

                Intent intent = new Intent(getApplication(), HomeActivity.class);
                Toast.makeText(getApplicationContext(), "This item has been added to your history", Toast.LENGTH_LONG);
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("GetReportNutrients");
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        Log.d("Fragment","Broadcast unregistered");
    }
}



