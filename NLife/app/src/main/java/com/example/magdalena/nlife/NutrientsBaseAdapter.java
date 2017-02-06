package com.example.magdalena.nlife;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Magdalena on 2/6/2017.
 */

// za ova ostana ushte delot vo koj gi polnime podatocite na listview preku povrzuvanje adapterot
public class NutrientsBaseAdapter extends BaseAdapter {


    ArrayList myList = new ArrayList();
    LayoutInflater inflater;
    Context context;


    public NutrientsBaseAdapter(Context context, ArrayList myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public ListData getItem(int position) {
        return (ListData) myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        ListData currentListData = getItem(position);

        mViewHolder.name.setText(currentListData.getName());
        mViewHolder.value.setText(currentListData.getValue());
        mViewHolder.unit.setText(currentListData.getUnit());

        return convertView;
    }

    private class MyViewHolder {
        TextView name, value, unit;


        public MyViewHolder(View item) {
            name = (TextView) item.findViewById(R.id.tvNutrientName);
            value = (TextView) item.findViewById(R.id.tvNutrientValue);
            unit = (TextView) item.findViewById(R.id.tvValueUnit);
        }
    }




}
