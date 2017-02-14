package com.example.magdalena.nlife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

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



      //  OVA E DODADENO VO KORIST NA 5 A DAY
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date now = new Date();
        String currentDayOfTheWeek = sdf.format(now);
        new getDataFromSQLite(getApplicationContext(),currentDayOfTheWeek).execute();


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

    //Kodot nadolu e za Marija, za da 5 a day, nagore od komentarov nemam menuvano nishto, osven shto onResume e premesten vo ovoj del pod komentarot
    // i ima edno delche dodadeno vo onCreate() ama ima komentar da go prepoznaesh :D
    //Ti mozhebi ke treba da gi razmestish mestata na povicite,vo zavisnoti od toa kade ke go pravish daily, ama toa e kodot.

    ArrayList<Tuple> allTuples; //tuka ti se torkite od bazata,ne se filtrirani po kategorija, zashto vo bazata ja nema, ke morash od GetRecipe da filtrirash po kategoiite za ovoshj i zelenchuk
    //taka da dobiesh json result, shto ke go obrabotish i smestish vo strukturi; pa posle toa od niv, da sporeduvash po name ili ndbno(ne znam shto ke posluzhi)
    // i samo torkite shto se sofpagjaat da gi obrabotuvash

    BroadcastReceiver broadcastReceiver5 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            allTuples=(ArrayList<Tuple>)intent.getExtras().get("Nutrients2");
            Log.d("HomeActivity","Broadcast received");
            for(int i=0;i<allTuples.size();i++)
                Log.d("Tuple["+i+"]=",allTuples.get(i).getName()+" quantity="+ allTuples.get(i).getQuantity());


        }
    };

    @Override
    protected void onResume() {
        super.onResume();
       /* Log.d("HomeActivity", "before addItem in onResume()");
        addItem();
        Log.d("HomeActivity", "after addItem in onResume()");
 */
        IntentFilter filter5 = new IntentFilter();
        filter5.addAction("GetDailyValues");
        registerReceiver(broadcastReceiver5, filter5);

    }

    @Override
    protected void onPause() {

        super.onPause();
        unregisterReceiver(broadcastReceiver5);
        Log.d("HomeActivity","Broadcast unregistered");

    }








    }

