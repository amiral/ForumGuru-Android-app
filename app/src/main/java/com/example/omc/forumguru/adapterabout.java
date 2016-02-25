package com.example.omc.forumguru;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ArrayAdapter;
 import android.widget.ImageView;
 import android.widget.TextView;

 import java.util.ArrayList;



public class adapterabout extends ArrayAdapter<Itemabout> {

    private final Context context;
    private final ArrayList<Itemabout> itemsArrayList;

    public adapterabout(Context context, ArrayList<Itemabout> itemsArrayList) {

        super(context, R.layout.aboutus, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.aboutus, parent, false);


        TextView valueView = (TextView) rowView.findViewById(R.id.ks);
        TextView val = (TextView) rowView.findViewById(R.id.kss);

        TextView valp = (TextView) rowView.findViewById(R.id.km);

         String m=itemsArrayList.get(position).getDescription();



        valueView.setText(itemsArrayList.get(position).getDescription());

        val.setText(itemsArrayList.get(position).gets());

        valp.setText(itemsArrayList.get(position).getm());


        return rowView;
    }
}