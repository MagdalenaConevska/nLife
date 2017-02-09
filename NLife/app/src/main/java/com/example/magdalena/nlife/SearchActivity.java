package com.example.magdalena.nlife;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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


public class SearchActivity extends MasterActivity {

    public String searchItem;
    HashMap<String, String> mapa;
    ArrayList<String> lista;
    ListView lv;
    String[] niza;
    ArrayAdapter<String> ad;
    EditText et;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Toast.makeText(getApplicationContext(),"Received broadcast", Toast.LENGTH_LONG).show();
            Log.d("SearchActivity","Received broadcast");
            Bundle bundle = intent.getExtras();
            Log.d("SearchActivity","Received broadcast");
            mapa = (HashMap<String, String>) bundle.get("mapa");

            // HashSet<String> set = (HashSet)mapa.entrySet();

            for(Map.Entry s : mapa.entrySet()){
                lista.add(s.toString());
            }
            niza = new String[lista.size()];
            for(int i=0; i<lista.size(); i++){
                niza[i] = lista.get(i);
            }

            showInList();

            Log.d("SearchActivity","number of items in list: " + lista.size());

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
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("searches", getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("search", searchItem);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), GetRecipesService.class);
                    //intent.putExtra("search", searchItem);

                    getApplicationContext().startService(intent);
                    Log.d("SearchActivity","Service should have started");
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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //make fragment transaction
                ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if(isConnected) {

                    ProductDetailFragment fragment = new ProductDetailFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_search, fragment);
                    fragmentTransaction.commit();
                    Button btn = (Button) findViewById(R.id.btnSearch);
                    btn.setVisibility(View.INVISIBLE);
                    //inside the new fragment construct the design according to the choice
                }
                else {
                    Toast.makeText(getApplicationContext(), "Device is not connected to internet. " +
                            "Internet connection is needed in order to get the info. " +
                            "Please connect your device to internet.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
