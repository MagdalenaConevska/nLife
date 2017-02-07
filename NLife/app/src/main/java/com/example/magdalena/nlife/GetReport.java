package com.example.magdalena.nlife;

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

/**
 * Created by Magdalena on 2/6/2017.
 */


public class GetReport extends AsyncTask<Void,Void,Void> {

        String result="";

        String apiUrl="https://api.nal.usda.gov/ndb/reports/?ndbno=";
        String apiKey="UMfhmQAzbJrzs6Ae872mqxrHB6SrHk54r18SMKMC";
        String ndbno="01009";

        static JSONObject jsonObject;
       ArrayList<Nutrient> lista = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(apiUrl + ndbno + "&type=f&format=json&api_key=" + apiKey);
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

            try{
                jsonObject = new JSONObject(result);
                JSONObject food = jsonObject.getJSONObject("food");
                JSONArray nutrients = food.getJSONArray("nutrients");
                for(int i=0; i<nutrients.length(); i++){

                    JSONObject one = nutrients.getJSONObject(i);
                    String name = one.getString("name");
                    String unit = one.getString("unit");
                    Double value = one.getDouble("value");
                    Nutrient n = new Nutrient(name, value, unit);
                    lista.add(n);
                }
            }catch (JSONException e){
                Log.e("JSONParser", e.getMessage(), e);
            }

            return null;
        }
    }

