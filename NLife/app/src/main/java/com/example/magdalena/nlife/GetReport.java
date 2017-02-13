package com.example.magdalena.nlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by Magdalena on 2/6/2017.
 */


public class GetReport extends AsyncTask<Void,Void,Void> {

    Context context;

    public GetReport(Context c) {
        context = c;
    }

    String result="";


    String apiUrl="https://api.nal.usda.gov/ndb/reports/?ndbno=";
    String apiKey="UMfhmQAzbJrzs6Ae872mqxrHB6SrHk54r18SMKMC";
    String ndbno="01009";
    String nut="&nutrients=208";

    static JSONObject jsonObject;
    ArrayList<Nutrient> lista = new ArrayList<>();

    @Override
    protected Void doInBackground(Void... params) {
        try {
            SharedPreferences sp = context.getSharedPreferences("ids", context.MODE_PRIVATE);
            ndbno=sp.getString("id",null);
            URL url = new URL(apiUrl + ndbno + nut +"&type=f&format=json&api_key=" + apiKey);
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
        TreeSet<String> set=new TreeSet<>();

        set.add("203");
        set.add("204");
        set.add("205");
        set.add("211");
        set.add("301");
        set.add("303");
        set.add("304");
        set.add("309");
        set.add("401");
        set.add("404");
        set.add("405");
        set.add("406");
        set.add("416");
        set.add("418");
        set.add("320");
        set.add("324");
        set.add("323");


        try{
            jsonObject = new JSONObject(result);
            JSONObject food = jsonObject.getJSONObject("report").getJSONObject("food");
            JSONArray nutrients = food.getJSONArray("nutrients");
            for(int i=0; i<nutrients.length(); i++){

                JSONObject one = nutrients.getJSONObject(i);
                String id=one.getString("nutrient_id");
                if(set.contains(id)) {
                    String name = one.getString("name");
                    String unit = one.getString("unit");
                    Double value = one.getDouble("value");
                    Nutrient n = new Nutrient(name, value, unit);
                    lista.add(n);
                }
            }
        }catch (JSONException e){
            Log.e("JSONParser", e.getMessage(), e);
        }

        Intent intent = new Intent();
        intent.setAction("GetReportNutrients");
        intent.putExtra("Nutrients",lista);
        context.sendBroadcast(intent);
        return null;
    }
}

