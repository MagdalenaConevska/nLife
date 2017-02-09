package com.example.magdalena.nlife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class ProductDetailFragment extends Fragment {

    ArrayList<Nutrient> lista;
    String name;
    //ListView lv;
    String[] niza;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("HistoryActivity","1. Broadcast received");
            ArrayList<Nutrient>lista= (ArrayList<Nutrient>)intent.getExtras().get("Nutrients");
            Log.d("HistoryActivity","2. Broadcast received");

        }
    };


    private OnFragmentInteractionListener mListener;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("GetReportNutrients");
        getActivity().registerReceiver(broadcastReceiver, filter);

        SharedPreferences sp = getActivity().getSharedPreferences("ids", getActivity().MODE_PRIVATE);
        name = sp.getString("name", null);
        Log.d("Fragment","got preference");
        TextView tv = (TextView)getView().findViewById(R.id.productName);
        Log.d("Fragment","got name");
        Log.d("Fragment","lista size" + lista.size());
        String []parts = name.split(",");
        StringBuilder sb = new StringBuilder();
        tv.setText(parts[0]);

        Log.d("Fragment","1");

        for(int i =1; i<parts.length-2;i++){
            sb.append(parts[i]);
            sb.append(",");
        }

        Log.d("Fragment","2");
        sb.append(parts[parts.length-2]);
        TextView tvA = (TextView)getView().findViewById(R.id.additionalInformation);
        tvA.setText(sb.toString());
        Log.d("Fragment","3");

        niza = new String[lista.size()];

        for(int i=0; i<lista.size(); i++){
            niza[i] = lista.get(i).toString();
        }

        Log.d("Fragment","Broadcast registered");
    }


    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
        Log.d("Fragment","Broadcast unregistered");
    }




    public static ProductDetailFragment newInstance() {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragment","onCreate");
        new GetReport(getActivity()).execute();
        Log.d("Fragment","executed");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       // View layout = inflater.inflate(R.layout.layout_list_item, container, false);
/*
        ArrayAdapter<Nutrient> ad = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, lista);
        Log.d("Fragment","4");

        ListView lv = (ListView)getView().findViewById(R.id.lvNutrients);

        Log.d("Fragment","5");
        Log.d("Fragment",ad.toString());
        lv.setAdapter(ad);
        Log.d("Fragment","6");

        */

        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ListView lv = (ListView) rootView.findViewById(R.id.lvNutrients);
        final ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, niza);

        Log.d("Fragment","4");
        //lv.setAdapter(ad);
        Log.d("Fragment","5");
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
