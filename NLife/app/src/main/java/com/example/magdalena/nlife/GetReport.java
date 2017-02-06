package com.example.magdalena.nlife;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Magdalena on 2/6/2017.
 */


public class GetReport extends AsyncTask<Void,Void,Void> {

        String result="";

        String apiUrl="https://api.nal.usda.gov/ndb/reports/?ndbno=";
        String apiKey="UMfhmQAzbJrzs6Ae872mqxrHB6SrHk54r18SMKMC";
        String ndbno="01009";

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
            return null;
        }
    }

