package com.example.magdalena.nlife;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class SearchActivity extends MasterActivity {

    public String searchItem;
    HashMap<String, String> mapa;
    ArrayList<String> lista;
    ListView lv;
    String[] niza;
    ArrayAdapter<String> ad;
    EditText et;
    private ProgressDialog dialog;

    String category;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("SearchActivity","Received broadcast");
            Bundle bundle = intent.getExtras();

            mapa = (HashMap<String, String>) bundle.get("mapa");

            Set<String> keys = mapa.keySet();
            for(String s : keys){
                lista.add(mapa.get(s));
            }
            niza = new String[lista.size()];
            for(int i=0; i<lista.size(); i++){
                niza[i] = lista.get(i);
            }


            showInList();

            Log.d("SearchActivity","number of items in list: " + lista.size());
            dialog.dismiss();

        }
    };

    public void showInList(){
        if(niza.length == 0) {
            Toast.makeText(this, "There is no such item in this category.", Toast.LENGTH_LONG).show();
        }

        ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, niza);
        //ad.clear();
        lv.setAdapter(ad);
        //lv.invalidate();
        Log.d("SearchActivity","end");

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("GetRecipes");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(this, GetRecipesService.class));
        unregisterReceiver(broadcastReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Button btn = (Button)findViewById(R.id.btnSearch);
        btn.setVisibility(View.VISIBLE);
        lista = new ArrayList<>();
        mapa = new HashMap<>();

        et = (EditText) findViewById(R.id.etSearch);


        Spinner spinner = (Spinner) findViewById(R.id.spinEnterCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // tv.setText("Spinner selected : ");
                //tv.setText(tv.getText() + parent.getItemAtPosition(position).toString());
              //  category=parent.getSelectedItem().toString();
                category=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });





        lv = (ListView)findViewById(R.id.lvSearch);
        Button button = (Button) findViewById(R.id.btnSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if(isConnected){
                    searchItem = et.getText().toString().trim();
                    Log.d("SearchActivity","Service starting");
                    //startService(new Intent(getApplicationContext(), GetRecipesService.class).putExtra("search", searchItem));
                    Log.d("GetRecipes","got product: " + searchItem);
                    if(searchItem.equals("")){
                        Toast.makeText(getApplicationContext(), "Please type recipe keyword", Toast.LENGTH_LONG).show();
                    }
                    else if(category.equals("Choose category..."))
                    {
                        Toast.makeText(getApplicationContext(), "Please choose category", Toast.LENGTH_LONG).show();
                    }
                    else {
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("searches", getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("search", searchItem);
                        editor.putString("category", category);
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), GetRecipesService.class);
                        //intent.putExtra("search", searchItem);

                        getApplicationContext().startService(intent);
                        Log.d("SearchActivity", "Service should have started");
                        showDialog();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Device is not connected to internet. " +
                            "Internet connection is needed in order to get the info. " +
                            "Please connect your device to internet.", Toast.LENGTH_LONG).show();
                }


            }
        });



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //make fragment transaction

                String name = lista.get(position);
                String id = "ovde nema nisto";
                Set<String> keys = mapa.keySet();
                Log.d("Fragment","name: " + name);
                if(mapa.containsValue(name)){
                    for(String s : keys){
                        if(name == mapa.get(s)){
                            id = s;
                        }
                    }

                    Log.d("SearchActivity","id: " + id);
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("ids", getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("id", id);
                    editor.putString("name", name);
                    editor.commit();


                }
                Log.d("SearchActivity","before fragment: ");



                ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if(isConnected) {


                    Intent intent = new Intent(getApplication(), DetailsActivity.class);
                    intent.putExtra("category", category);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Device is not connected to internet. " +
                            "Internet connection is needed in order to get the info. " +
                            "Please connect your device to internet.", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void showDialog(){
        dialog=new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setTitle("Getting recipes");
        dialog.setMessage("Getting recipes...please wait");
        dialog.show();
    }
}
