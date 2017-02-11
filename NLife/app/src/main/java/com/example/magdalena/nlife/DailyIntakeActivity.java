package com.example.magdalena.nlife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Map;

import java.util.Set;

import java.util.TreeSet;


public class DailyIntakeActivity extends  MasterActivity  {

    Map<String,Double> map;
    ArrayList<Tuple> tuples;
    String day;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    Spinner spinner5;
    Set<String> values;

    BroadcastReceiver broadcastReceiver1=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            map=(Map<String,Double>)intent.getExtras().get("Nutrients1");
            new getDataFromSQLite(getApplicationContext(),day).execute();
        }
    };

    BroadcastReceiver broadcastReceiver2=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            tuples=(ArrayList<Tuple>)intent.getExtras().get("Nutrients2");
            showGraph();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_intake);
        day=getIntent().getStringExtra("Den");
        //day="Monday";
        //showGraph();
        spinner1=(Spinner)this.findViewById(R.id.spinnerNutrients1);
        spinner2=(Spinner)this.findViewById(R.id.spinnerNutrients2);
        spinner3=(Spinner)this.findViewById(R.id.spinnerNutrients3);
        spinner4=(Spinner)this.findViewById(R.id.spinnerNutrients4);
        spinner5=(Spinner)this.findViewById(R.id.spinnerNutrients5);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.nutrients_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);
        values=new TreeSet<String>();

        Button buttonShow=(Button)this.findViewById(R.id.buttonNutrients);
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(map==null || tuples==null) {
                    getValues();
                    Log.d("DailyIntakeActivity","getValues()");
                }
                else {
                    showGraph();
                    Log.d("DailyIntakeActivity","showGraph()");
                }
            }
        });

        // new getDataFromSQLite(getApplicationContext()).execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver1);
        unregisterReceiver(broadcastReceiver2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("GetRecommendedValues");
        registerReceiver(broadcastReceiver1, filter1);
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("GetDailyValues");
        registerReceiver(broadcastReceiver2, filter2);
    }

/*
    private void showGraph(){
       GraphView graph = (GraphView) findViewById(R.id.graph);

       /*GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, -2),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        PointsGraphSeries<DataPoint> series4 = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 2),
                new DataPoint(2, 0),
                new DataPoint(3, -1),
                new DataPoint(4, 3)
        });
        graph.addSeries(series4);
        series4.setColor(Color.RED);
        series4.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(7);
                canvas.drawLine(x-10, y, x+10, y, paint);
               // canvas.drawLine(x+20, y-20, x-20, y+20, paint);
            }
        });
*/

    private void getValues(){
        if(!values.isEmpty()){
            values.clear();
        }
        values.add(spinner1.getSelectedItem().toString());
        values.add(spinner2.getSelectedItem().toString());
        values.add(spinner3.getSelectedItem().toString());
        values.add(spinner4.getSelectedItem().toString());
        values.add(spinner5.getSelectedItem().toString());

        int age=Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString(getResources().getString(R.string.pref_age_key),getResources().getString(R.string.pref_age_defaultValue)));
        String gender=PreferenceManager.getDefaultSharedPreferences(this).getString(getResources().getString(R.string.pref_gender_key),getResources().getString(R.string.pref_gender_defaultValue));
        int category=0;
        if(age==0 || age==1) {
            category = 0;
        }
        else if(age<4) {
            category = 1;
        }
        else {
            if (gender.equals(getResources().getString(R.string.male))) {
                category = 3;
            } else {
                boolean pregnancy = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getResources().getString(R.string.pref_pr_key), false);
                if (pregnancy) {
                    category = 2;
                } else {
                    category = 3;
                }
            }
        }
        new GetRecommendedValues(getApplicationContext()).execute(category);
    }


    private void showGraph(){
        //tuka da se prikazat vrednostite za 5te izbrani nutrienti

        GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, -1),
                new DataPoint(0.2, 5),
                new DataPoint(0.4, 3),
                new DataPoint(0.6, 4),
                new DataPoint(0.8, 2),
                new DataPoint(1, 6),
                new DataPoint(1.2, -1),
                new DataPoint(1.4, 5),
                new DataPoint(1.6, 3),
                new DataPoint(1.8, 5),
                new DataPoint(2, 2),
                new DataPoint(2.2, 6),
                new DataPoint(2.4, -1),
                new DataPoint(2.6, 5),
                new DataPoint(2.8, 3),
                new DataPoint(3, 6),
                new DataPoint(3.2, 5),//
                new DataPoint(3.4, 0),
                new DataPoint(3.6, 0),
                new DataPoint(3.8, 0),
                new DataPoint(4, 0)
        });
        graph.addSeries(series);

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });


        series.setSpacing(1);
        series.setDataWidth(0.2);


// draw values on top
        series.setDrawValuesOnTop(false);
        series.setValuesOnTopColor(Color.RED);

     /*   StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"CH", "F", "Pr","Mi"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
*/

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }



}
