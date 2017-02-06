package com.example.magdalena.nlife;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;


public class SearchActivity extends MasterActivity {

     public static String searchItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText et = (EditText) findViewById(R.id.etSearch);
        searchItem = et.getText().toString().trim();

        new GetRecipes().execute();

        Spinner spinner = (Spinner) findViewById(R.id.spinEnterCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ListView lv = (ListView)findViewById(R.id.lvSearch);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //make fragment transaction

                ProductDetailFragment fragment = new ProductDetailFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               // fragmentTransaction.replace(R.id.activity_search, fragment);
                fragmentTransaction.commit();
                //inside the new fragment construct the design according to the choice
            }
        });


    }
}
