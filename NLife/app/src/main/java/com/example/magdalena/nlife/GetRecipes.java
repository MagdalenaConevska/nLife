package com.example.magdalena.nlife;

import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Magdalena on 2/4/2017.
 */

public class GetRecipes extends AsyncTask<Void,Void,Void> {

    HashMap<String, String> mapa;
    Context context;

    String result="";

    String apiUrl="https://api.nal.usda.gov/ndb/search/?format=json&q=";
    String apiKey="UMfhmQAzbJrzs6Ae872mqxrHB6SrHk54r18SMKMC";

    String searchItem;
    static JSONObject jsonObject;

    public GetRecipes(Context c){
        context = c;
        mapa = new HashMap<>();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        SharedPreferences sp = context.getSharedPreferences("searches", context.MODE_PRIVATE);
        searchItem = sp.getString("search", null);
        Log.d("GetRecipes","got product: " + searchItem);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Intent intent = new Intent();
        intent.setAction("GetRecipes");
        intent.putExtra("mapa", mapa);
        context.sendBroadcast(intent);


    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(apiUrl + searchItem + "&max=25" + "&api_key=" + apiKey);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                result=stringBuilder.toString();
                // return stringBuilder.toString();
                Log.d("Hoorayy","Response from url: "+result);
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        //parsing
        try{
            jsonObject = new JSONObject(result);
            JSONArray item = jsonObject.getJSONObject("list").getJSONArray("item");
            for(int i=0; i<item.length(); i++){

                JSONObject one = item.getJSONObject(i);
                String name = one.getString("name");
                String ndbno = one.getString("ndbno");
                mapa.put(ndbno, name);
            }
            Log.d("GetRecipes","number of items: " + item.length() + " number of items in map: " + mapa.size());
            // Toast.makeText(context, "number of items: " + item.length(), Toast.LENGTH_LONG).show();
        } catch (JSONException e){
            Log.e("JSONParser", e.getMessage(), e);
        }


        return null;
    }


}



