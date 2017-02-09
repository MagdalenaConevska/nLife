package com.example.magdalena.nlife;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;

public class HistoryActivity extends  MasterActivity  {

    ArrayList<Nutrient>lista;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Nutrient>lista=(ArrayList<Nutrient>)intent.getExtras().get("Nutrients");
            Log.d("HistoryActivity","Broadcast received");
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        Spinner spinner = (Spinner) findViewById(R.id.spinnerDays);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        new GetReport(getApplicationContext()).execute();


    }


    @Override
    protected void onResume() {
        super.onRestart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("GetReportNutrients");
        registerReceiver(broadcastReceiver, filter);
        Log.d("HistoryActivity","Broadcast registered");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        Log.d("HistoryActivity","Broadcast unregistered");
    }



    public void startDailyIntakeActivity(View v){

        Intent intent = new Intent(this, DailyIntakeActivity.class);
        startActivity(intent);

    }

    public void startWeeklyIntakeActivity(View v){

        Intent intent = new Intent(this, WeeklyIntakeActivity.class);
        startActivity(intent);

    }

    public void insertInDatabaseTable(View v){
        //ovde kodot za insert

        //samo da se prezeme vistinskata lista, name,datum,ndbno i kolichina! dr e ok

        // ArrayList<Nutrient> lista=new ArrayList<>();

        String name="apple";
        String datum="09.02.2017";
        String ndbno="01009";
        int kolichina=2;


        double protein=0,lipid=0,carbo=0,glucose=0,calcium=0;
        double iron=0,mg=0,zinc=0,vitC=0,thiamin=0,ribo=0;
        double niacin=0,vitB6=0,vitB12=0,vitA=0,vitD=0,vitE=0;


        for (int i=0;i<lista.size();i++){

            if(lista.get(i).name.equals("Protein")){

                protein=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Total lipid (fat)")){

                lipid=lista.get(i).recorded;
            }

            else if (lista.get(i).name.equals("Carbohydrate, by difference")){

                carbo=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Glucose (dextrose)")){

                glucose=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Calcium, Ca")){

                calcium=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Iron, Fe")){

                iron=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Magnesium, Mg")){

                mg=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Zinc, Zn")){

                zinc=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin C, total ascorbic acid")){

                vitC=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Thiamin")){

                thiamin=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Riboflavin")){

                ribo=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Niacin")){

                niacin=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin B-6")){

                vitB6=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin B-12")){
                vitB12=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin A, RAE")){

                vitA=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin D (D2 + D3)")){

                vitD=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin E (alpha-tocopherol)")){

                vitE=lista.get(i).recorded;
            }


        }

        NutrientsDBHelper dbHelper=new NutrientsDBHelper(this); //this=context
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put(NutrientDBEntry.COLUMN_DATE,datum);
        values.put(NutrientDBEntry.COLUMN_NDBNO,ndbno);
        values.put(NutrientDBEntry.COLUMN_PRODUCT_NAME,name);
        values.put(NutrientDBEntry.COLUMN_QUANTITY,kolichina);


        values.put(NutrientDBEntry.COLUMN_PROTEIN,protein);
        values.put(NutrientDBEntry.COLUMN_TOTAL_LIPID,lipid);
        values.put(NutrientDBEntry.COLUMN_CARBOHYDRATE,carbo);

        values.put(NutrientDBEntry.COLUMN_GLUCOSE,glucose);
        values.put(NutrientDBEntry.COLUMN_CALCIUM,calcium);
        values.put(NutrientDBEntry.COLUMN_IRON,iron);

        values.put(NutrientDBEntry.COLUMN_MAGNESIUM,mg);
        values.put(NutrientDBEntry.COLUMN_ZINC,zinc);
        values.put(NutrientDBEntry.COLUMN_VITAMIN_C,vitC);

        values.put(NutrientDBEntry.COLUMN_THIAMIN,thiamin);
        values.put(NutrientDBEntry.COLUMN_RIBOFLAVIN,ribo);
        values.put(NutrientDBEntry.COLUMN_NIACIN,niacin);

        values.put(NutrientDBEntry.COLUMN_VITAMIN_B6,vitB6);
        values.put(NutrientDBEntry.COLUMN_VITAMIN_B12,vitB12);
        values.put(NutrientDBEntry.COLUMN_VITAMIN_A,vitA);

        values.put(NutrientDBEntry.COLUMN_VITAMIN_D,vitD);
        values.put(NutrientDBEntry.COLUMN_VITAMIN_E,vitE);

        db.insert(NutrientDBEntry.TABLE_NAME,null,values);


    }



}