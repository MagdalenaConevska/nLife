package com.example.magdalena.nlife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FiveADayActivity extends MasterActivity {

    ArrayList<String> fruits;
    ArrayList<String> veggies;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_aday);

        fruits = new ArrayList<>();
        veggies = new ArrayList<>();



        //  OVA E DODADENO VO KORIST NA 5 A DAY
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date now = new Date();
        String currentDayOfTheWeek = sdf.format(now);
        new getDataFromSQLite(getApplicationContext(),currentDayOfTheWeek).execute();

        /////  new GetFruits(this).execute();
        // Log.d("Fruits", "execute");
        //  new GetVeggies(this).execute();
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

      /*      Bundle bundle = intent.getExtras();
            fruits = bundle.getStringArrayList("listFruits");
            Log.d("Fruits", "listFruits");
        //    veggies = bundle.getStringArrayList("listVeggies");

        */    //Log.d("Fruits", veggies.size() + " ");


            allTuples=(ArrayList<Tuple>)intent.getExtras().get("Nutrients2");
            Log.d("HomeActivity","Broadcast received");
            for(int i=0;i<allTuples.size();i++)
                Log.d("Tuple["+i+"]=",allTuples.get(i).getName()+" quantity="+ allTuples.get(i).getQuantity());

            //  count = 0;
            //   checkDifference();
        }
    };

    public void checkDifference(){

        for(Tuple t : allTuples){
            for(String s : fruits){
                if(t.name == s){
                    count += t.quantity;
                }
            }
            for(String s : veggies){
                if(t.name == s){
                    count += t.quantity;
                }
            }
        }

    }

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

        SharedPreferences sp = getApplicationContext().getSharedPreferences("categories", getApplicationContext().MODE_PRIVATE);
        TextView tv = (TextView)findViewById(R.id.tv5);
        count = sp.getInt("count", 22);
        tv.setText(count + " grams");
        tv = (TextView)findViewById(R.id.cetvrto);
        if(count < 450)
        tv.setText(450 - count  + " grams");
        else{
            tv.setText(0 + " grams");
        }



    }

    @Override
    protected void onPause() {

        super.onPause();
        unregisterReceiver(broadcastReceiver5);
        Log.d("HomeActivity","Broadcast unregistered");

    }
}
