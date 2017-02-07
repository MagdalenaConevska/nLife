package com.example.magdalena.nlife;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Toni on 01.02.2017.
 */

public class GridViewAdapter extends BaseAdapter {

    Context context;

    public GridViewAdapter(Context c){
        context=c;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.grid_item_layout,parent,false);
        }
        TextView textView=(TextView)convertView.findViewById(R.id.textView);
        ImageButton imageButton=(ImageButton)convertView.findViewById(R.id.imageButton);
        setListeners(textView,imageButton,position);
        return convertView;
    }

    private void setListeners(TextView textView, ImageButton imageButton, int pos){
        switch (pos){
            case 0:
                textView.setText(context.getResources().getString(R.string.gridSearch));
                imageButton.setImageResource(R.drawable.search_img);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"0 GridItem clicked",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(context,SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                textView.setText(context.getResources().getString(R.string.gridDaily));
                imageButton.setImageResource(R.drawable.daily_intake_img);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"1 GridItem clicked",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(context,DailyIntakeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                break;
            case 2:
                textView.setText(context.getResources().getString(R.string.gridFive));
                imageButton.setImageResource(R.drawable.five_a_day_img);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"2 GridItem clicked",Toast.LENGTH_LONG).show();
                        //show dialog instead of next code
                        int age=Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.pref_age_key),context.getResources().getString(R.string.pref_age_defaultValue)));
                        String gender=PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.pref_gender_key),context.getResources().getString(R.string.pref_gender_defaultValue));
                        int category=0;
                        if(age==0 || age==1) {
                            category = 0;
                        }
                        else if(age<4) {
                            category = 1;
                        }
                        else {
                            if (gender.equals(context.getResources().getString(R.string.male))) {
                                category = 3;
                            } else {
                                boolean pregnancy = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getResources().getString(R.string.pref_pr_key), false);
                                if (pregnancy) {
                                    category = 2;
                                } else {
                                    category = 3;
                                }
                            }
                        }
                        new GetRecommendedValues(context).execute(category);
                    }
                });
                break;
            case 3:
                textView.setText(context.getResources().getString(R.string.gridHistory));
                imageButton.setImageResource(R.drawable.history_img);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"3 GridItem clicked",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(context,HistoryActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                break;
        }
    }
}
