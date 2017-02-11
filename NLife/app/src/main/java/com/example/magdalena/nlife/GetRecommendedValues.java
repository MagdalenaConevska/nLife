package com.example.magdalena.nlife;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Toni on 04.02.2017.
 */

public class GetRecommendedValues extends AsyncTask<Integer,Void,HashMap<String,Double>> {

    Context context;

    public GetRecommendedValues(Context c){
        context=c;
    }

    @Override
    protected HashMap<String, Double> doInBackground(Integer... params) {
        HashMap<String,Double>map=new HashMap<String, Double>();
        int id=params[0];
        try {
            XmlPullParser xmlPullParser = context.getResources().getXml(R.xml.recommended_daily_values);
            xmlPullParser.next();
            Log.d("GetRecommendedValues", "enter");
            String nutrientName=null;
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xmlPullParser.getName().equals("nutrient")) {
                        nutrientName=xmlPullParser.getAttributeValue(0);
                        Log.d("GetRecommendedValues", xmlPullParser.getAttributeName(0) + "->" + xmlPullParser.getAttributeValue(0));
                        Log.d("GetRecommendedValues", "-" + xmlPullParser.getAttributeName(1) + "->" + xmlPullParser.getAttributeValue(1));
                        Log.d("GetRecommendedValues", "-" + xmlPullParser.getAttributeName(2) + "->" + xmlPullParser.getAttributeValue(2));
                    } else if (xmlPullParser.getName().equals("category")) {
                        Log.d("GetRecommendedValues", "-" + xmlPullParser.getAttributeName(0) + "->" + xmlPullParser.getAttributeValue(0));
                        Log.d("GetRecommendedValues", "--" + xmlPullParser.getAttributeName(1) + "->" + xmlPullParser.getAttributeValue(1));
                        String value=xmlPullParser.getAttributeValue(1);
                        int categoryID=Integer.parseInt(xmlPullParser.getAttributeValue(0));
                        if(!value.equals("") && categoryID==id) {
                            map.put(nutrientName, Double.parseDouble(xmlPullParser.getAttributeValue(1)));
                        }
                    }
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            Log.d("GetRecommendedValues", "error");
            Log.d("GetRecommendedValues", e.getMessage());
        }
        return map;
    }

    @Override
    protected void onPostExecute(HashMap<String, Double> stringDoubleHashMap) {
        super.onPostExecute(stringDoubleHashMap);
        Set<Map.Entry<String, Double>> set = stringDoubleHashMap.entrySet();
        for (Map.Entry<String, Double> entry : set) {
            Log.d("GetRecommendedValues",entry.getKey()+"->"+entry.getValue());
        }
        Intent intent = new Intent();
        intent.setAction("GetRecommendedValues");
        intent.putExtra("Nutrients1",stringDoubleHashMap);
        context.sendBroadcast(intent);


    }
}
