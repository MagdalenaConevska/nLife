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

import android.text.TextUtils;
import android.view.View;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
    GraphView graph;
    TextView textViews[];
    ArrayList<Double>recommended;
    ArrayList<Double>intake;

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

            Log.d("Tuples"," received");
            Log.d("Tuples size ",tuples.size()+"");
            //Log.d("Tuple1",tuples.get(0).getName());

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
        values=new LinkedHashSet<>();
        recommended=new ArrayList<>();
        intake=new ArrayList<>();

        Button buttonShow=(Button)this.findViewById(R.id.buttonNutrients);
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
            }
        });

        textViews=new TextView[5];
        textViews[0]=(TextView)this.findViewById(R.id.dailyNutrient1);
        textViews[1]=(TextView)this.findViewById(R.id.dailyNutrient2);
        textViews[2]=(TextView)this.findViewById(R.id.dailyNutrient3);
        textViews[3]=(TextView)this.findViewById(R.id.dailyNutrient4);
        textViews[4]=(TextView)this.findViewById(R.id.dailyNutrient5);
        invisibleTextViews();
    }

    private void invisibleTextViews(){
        for(TextView t:textViews){
            t.setVisibility(View.GONE);
        }
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

    private void showGraph() {
        if (!recommended.isEmpty()) {
            recommended.clear();
        }
        if (!intake.isEmpty()) {
            intake.clear();
        }
        invisibleTextViews();
        graph = (GraphView) findViewById(R.id.graph);
        showBars();
        showDots();
        Iterator<String> it = values.iterator();
        int i = 0;
        while (it.hasNext()) {
            String s1 = getResources().getString(R.string.nutrient_intake);
            String s2 = TextUtils.htmlEncode(s1);
            String name = it.next();
            if (recommended.size() > i && intake.size() > i) {
                String s3 = String.format(s2, name, recommended.get(i), intake.get(i));
                textViews[i].setText(s3);
                textViews[i].setVisibility(View.VISIBLE);
            }
            i++;
        }
    }

    private void showDots() {
        Iterator<String> it = values.iterator();
        DataPoint[] dpArray = new DataPoint[values.size()];
        int i = 0;
        while (it.hasNext()) {
            String currentNutrient = it.next();
            dpArray[i] = new DataPoint(i + 1, map.get(currentNutrient));
            recommended.add(map.get(currentNutrient));
            i++;
        }
        PointsGraphSeries<DataPoint> series4 = new PointsGraphSeries<>(dpArray);
        graph.addSeries(series4);
        series4.setColor(Color.RED);
        series4.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(7);
                canvas.drawLine(x - 10, y, x + 10, y, paint);
            }
        });
    }

    private void showBars() {
        //tuka da se prikazat vrednostite za 5te izbrani nutrienti
        //vo setot values se potrebnite nutrienti(5te)
        //vo tuples se site vrateni torki od bazata
        graph.removeAllSeries();
        Log.d("ShowGraph ", "started");
        Log.d("Tuples length ", tuples.size() + "");
        ArrayList<Double> vkupno = new ArrayList<Double>();
        //vo vkupno, sekoj element, e vkupnata vrednost za nutrientot pod toj broj
        int brNutrienti = values.size();
        Log.d("Values length", brNutrienti + "");
        for (int i = 0; i < brNutrienti; i++) {
            vkupno.add(i, 0.0);
        }
        Iterator<String> it = values.iterator();
        String currentNutrient = null;
        double dodadi = 0;
        for (int j = 0; j < brNutrienti; j++) {
            currentNutrient = it.next();
            for (int i = 0; i < tuples.size(); i++) {
                Tuple currentTuple = tuples.get(i);
                if (currentNutrient.equals("Protein")) {
                    dodadi = (currentTuple.getProtein()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Total lipid (fat)")) {
                    dodadi = (currentTuple.getLipid()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Carbohydrate")) {
                    dodadi = (currentTuple.getCarbohydrate()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Glucose")) {
                    dodadi = (currentTuple.getGlucose()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Calcium")) {
                    dodadi = (currentTuple.getCalcium()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Iron")) {
                    dodadi = (currentTuple.getIron()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Magnesium")) {
                    dodadi = (currentTuple.getMagnesium()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Zinc")) {
                    dodadi = (currentTuple.getZinc()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Vitamin C")) {
                    dodadi = (currentTuple.getVitaminC()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Thiamin")) {
                    dodadi = (currentTuple.getThiamin()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Riboflavin")) {
                    dodadi = (currentTuple.getRibofavin()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Niacin")) {
                    dodadi = (currentTuple.getNiacin()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Vitamin B6")) {
                    dodadi = (currentTuple.getVitaminB6()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Vitamin B12")) {
                    dodadi = (currentTuple.getVitaminB12()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("VitaminA")) {
                    dodadi = (currentTuple.getVitaminA()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Vitamin D")) {
                    dodadi = (currentTuple.getVitaminD()) * currentTuple.getQuantity() / 100;
                } else if (currentNutrient.equals("Vitamin E")) {
                    dodadi = (currentTuple.getVitaminE()) * currentTuple.getQuantity() / 100;
                }
                dodadi = dodadi + vkupno.get(j);
                vkupno.set(j, dodadi);
                dodadi = 0;
            }
        }
        Log.d("Posle polenje na vkupno", "sfsfsdf");
        //posle ova vo vkupno se sodrzhat y vrednostite za grafikot
        DataPoint[] dpArray = new DataPoint[vkupno.size()];
        for (int i = 0; i < vkupno.size(); i++) {
            dpArray[i] = new DataPoint(i + 1, vkupno.get(i));
            intake.add(vkupno.get(i));
            Log.d("y(" + i + ")=", vkupno.get(i) + "");
        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(dpArray);
        graph.addSeries(series);
        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });
        series.setSpacing(1);
        series.setDataWidth(0.2);
        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);
        /*   StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"CH", "F", "Pr","Mi"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        */
        //graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }
}
