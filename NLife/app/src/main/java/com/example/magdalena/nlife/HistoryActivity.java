package com.example.magdalena.nlife;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HistoryActivity extends  MasterActivity  {

    ArrayList<Nutrient>lista;
    ArrayList<String> products = new ArrayList<>();
    String den="Monday";
    int pozicija=0;

    ArrayList<Tuple> tuples;
// vo tuples se potrebnite data za listata

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            lista=(ArrayList<Nutrient>)intent.getExtras().get("Nutrients");
            Log.d("HistoryActivity","Broadcast received");
            Toast.makeText(getApplicationContext(),"Broadcast received",Toast.LENGTH_LONG).show();

        }
    };

    BroadcastReceiver broadcastReceiver4=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            tuples=(ArrayList<Tuple>)intent.getExtras().get("Nutrients2");
             Log.d("Tuples"," received");
            Log.d("Tuples size ",tuples.size()+"");
            for(int i=0;i<tuples.size();i++)
            Log.d("Tuple["+i+"]=",tuples.get(i).getName()+" quantity="+ tuples.get(i).getQuantity());

            String[] izvor= new String [tuples.size()];

            for(int i=0;i<tuples.size();i++){

              //  izvor[i]=tuples.get(i).getName()+" "+tuples.get(i).getQuantity()+"g";
                izvor[i]=tuples.get(i).getName();

            }

            TextView tvEatenThings = (TextView) findViewById(R.id.eatenThings);
            String text ="Products you have eaten on " + den + ":";
            tvEatenThings.setText(text);

            ArrayAdapter<String> adapterHistory = new ArrayAdapter<String>(getApplicationContext(), R.layout.lv_history_item,izvor);

            ListView listView = (ListView)findViewById(R.id.lvHistory);
            listView.setAdapter(adapterHistory);

        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date now = new Date();
        String currentDayOfTheWeek = sdf.format(now);

        if(currentDayOfTheWeek.equals("Monday")) pozicija=0;
        else if(currentDayOfTheWeek.equals("Tuesday")) pozicija=1;
        else if(currentDayOfTheWeek.equals("Wednesday")) pozicija=2;
        else if(currentDayOfTheWeek.equals("Thursday")) pozicija=3;
        else if(currentDayOfTheWeek.equals("Friday")) pozicija=4;
        else if(currentDayOfTheWeek.equals("Saturday")) pozicija=5;
        else if(currentDayOfTheWeek.equals("Sunday")) pozicija=6;



        if(currentDayOfTheWeek.equals("Sunday")){

            Button weekly = (Button) findViewById(R.id.btnWeekly);
            weekly.setEnabled(true);

        }
        else{

            Button weekly = (Button) findViewById(R.id.btnWeekly);
            weekly.setEnabled(false);
        }




         Spinner spinner = (Spinner) findViewById(R.id.spinnerDays);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // tv.setText("Spinner selected : ");
                //tv.setText(tv.getText() + parent.getItemAtPosition(position).toString());
                den=parent.getItemAtPosition(position).toString();
                if(den.equals("Monday")) pozicija=0;
                else if(den.equals("Tuesday")) pozicija=1;
                else if(den.equals("Wednesday")) pozicija=2;
                else if(den.equals("Thursday")) pozicija=3;
                else if(den.equals("Friday")) pozicija=4;
                else if(den.equals("Saturday")) pozicija=5;
                else if(den.equals("Sunday")) pozicija=6;

                new getDataFromSQLite(getApplicationContext(),den).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        spinner.setSelection(pozicija);

        new getDataFromSQLite(this,den).execute();


      /*  String[] izvor= new String [tuples.size()];

        for(int i=0;i<tuples.size();i++){

                izvor[i]=tuples.get(i).getName()+"          "+tuples.get(i).getQuantity()+"g";

        }

        ArrayAdapter<String> adapterHistory = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,izvor);

        ListView listView = (ListView)findViewById(R.id.lvHistory);
        listView.setAdapter(adapterHistory); */


    }


    @Override
    protected void onResume() {
        super.onRestart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("GetReportNutrients");
        registerReceiver(broadcastReceiver, filter);
        Log.d("HistoryActivity","Broadcast registered");

        IntentFilter filter4 = new IntentFilter();
        filter4.addAction("GetDailyValues");
        registerReceiver(broadcastReceiver4, filter4);

        Log.d("HistoryActivity","Broadcast4 registered");

        SharedPreferences sp = getApplicationContext().getSharedPreferences("history", getApplicationContext().MODE_PRIVATE);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){

            Log.d("HistoryActivity", "bundle isn't null");
            Boolean truth = bundle.getBoolean("lista");
            Log.d("HistoryActivity", truth.toString());
            if(!truth){
                products = new ArrayList<>();
            } else {
                products = new ArrayList<>();
                int size = sp.getInt("size", 0 );
                for(int i = 0; i<size; i++){
                    products.add(sp.getString("key_" + i, null));
                }
            }
            String p = bundle.get("product").toString();
            Log.d("HistoryActivity", p);
            products.add(p);
            Log.d("HistoryActivity", "it's not it");
            Log.d("HistoryActivity", "got list");
            ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
            Log.d("HistoryActivity", "got adapter");
            ListView lv = (ListView)findViewById(R.id.lvHistory);
            Log.d("HistoryActivity", "got listview");
            lv.setAdapter(ad);
            Log.d("HistoryActivity", "adapter set");
            Log.d("HistoryActivity", products.size() + " ");

        }


        SharedPreferences.Editor editor = sp.edit();

        int i = 0;
        editor.putInt("size", products.size());

        for(String s : products){
            String key = "key_" + i;
            editor.putString(key, s);
            i++;
        }

        editor.commit();


    }

    @Override
    protected void onPause() {

        super.onPause();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiver4);
        Log.d("HistoryActivity","Broadcast unregistered");

    }



    public void startDailyIntakeActivity(View v){

        Intent intent = new Intent(this, DailyIntakeActivity.class);
        intent.putExtra("Den",den);
        startActivity(intent);

    }

    public void startWeeklyIntakeActivity(View v){

        Intent intent = new Intent(this, WeeklyIntakeActivity.class);
        startActivity(intent);

    }

    /*
    public void insertInDatabaseTable(View v){
        //ovde kodot za insert

        //samo da se prezeme vistinskata lista, name,datum,ndbno i kolichina! dr e ok

        // ArrayList<Nutrient> lista=new ArrayList<>();

<<<<<<< HEAD
        String name="cheese";

        //pri vnes i ime na den da dodademe kaj datum, pa so like da prochitame posle
        String datum="07.02.2017";
        String ndbno="01009";
        int kolichina=1;
=======
        String name="apple";
        //String datum="09.02.2017";
        String ndbno="01008";
        int kolichina=2;
>>>>>>> cd8273e22089b9561cb85d3c576743b4ae8314f7

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy EEEE");
        String datum = df.format(c.getTime());

        double protein=0,lipid=0,carbo=0,glucose=0,calcium=0;
        double iron=0,mg=0,zinc=0,vitC=0,thiamin=0,ribo=0;
        double niacin=0,vitB6=0,vitB12=0,vitA=0,vitD=0,vitE=0;

       Log.d("msg:","Pred for");


        for (int i=0;i<lista.size();i++){

            Log.d("Vo for",i+"");

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
            else if (lista.get(i).name.equals("Vitamin D")){

                vitD=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin E (alpha-tocopherol)")){

                vitE=lista.get(i).recorded;
            }


        }


        Log.d("msg:","Posle for");
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

        Log.d("msg:","Pred insert");

        db.insert(NutrientDBEntry.TABLE_NAME,null,values);

        Log.d("msg:","Posle insert");


    }

*/

}