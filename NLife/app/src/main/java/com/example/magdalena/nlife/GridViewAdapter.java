package com.example.magdalena.nlife;

import android.content.Context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.grid_item_layout,parent,false);
        }
        TextView textView=(TextView)convertView.findViewById(R.id.textView);
        textView.setText("Search");
        ImageButton imageButton=(ImageButton)convertView.findViewById(R.id.imageButton);
        imageButton.setImageResource(R.drawable.search_img);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"GridItem clicked",Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
