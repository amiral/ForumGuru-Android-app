package com.example.omc.forumguru;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Forumadapter extends ArrayAdapter<ParseObject> {

    protected Context mcontext;
    protected List<ParseObject> mtopic;

    private Date date;
    public Forumadapter(Context context,List<ParseObject> status) {
        super(context,R.layout.forum_item,status);
        mcontext=context;
        mtopic=status;
    }

    public static class ViewHolder{

        TextView topic;
        TextView topic_by;
        TextView topic_cat;
        TextView tt,tm;
        ImageView s;
    }


    public View getView(final int position, View converterView, ViewGroup parent){
        final ViewHolder holder;
        if(converterView==null){
            converterView= LayoutInflater.from(mcontext).inflate(R.layout.forum_item,null);
            holder=new ViewHolder();
            holder.s=(ImageView)converterView.findViewById(R.id.u_pic);
            holder.tt=(TextView)converterView.findViewById(R.id.tm);


            holder.topic_by=(TextView)converterView.findViewById(R.id.t_by);
            holder.topic=(TextView)converterView.findViewById(R.id.t);
            holder.topic_cat=(TextView)converterView.findViewById(R.id.t_cat);
            converterView.setTag(holder);

        }
        else{
            holder=(ViewHolder)converterView.getTag();
        }

        ParseObject statusobj=mtopic.get(position);

        //title
        String username=statusobj.getString("topicby");

        holder.topic_by.setText(username);
        //content
        String sta=statusobj.getString("topic");
        holder.topic.setText(sta);

        String m=statusobj.getString("topic_cat");


        int a=R.drawable.a;
        if(m.equals("Forum Hub")){
            holder.s.setImageResource(R.drawable.a);
        }

        else if(m.equals("Web development")){
            holder.s.setImageResource(R.drawable.b);
        }

        else if(m.equals("Programming languages")){
            holder.s.setImageResource(R.drawable.c);
        }
        else if(m.equals("Operating systems")){
            holder.s.setImageResource(R.drawable.d);
        }
        else if(m.equals("Dbms")){
            holder.s.setImageResource(R.drawable.e);
        }
        else if(m.equals("Robotics")){
            holder.s.setImageResource(R.drawable.f);


        }

        else if(m.equals("Internet of things")){
            holder.s.setImageResource(R.drawable.g);

        }

        else if(m.equals("Arduino")){
            holder.s.setImageResource(R.drawable.h);

        }


        else if(m.equals("Raspberry pi")){
            holder.s.setImageResource(R.drawable.i);

        }

        else if(m.equals("Automobiles")){
            holder.s.setImageResource(R.drawable.j);

        }

        else if(m.equals("Social media")){
            holder.s.setImageResource(R.drawable.k);

        }


        else if(m.equals("Gadgets")){
            holder.s.setImageResource(R.drawable.l);

        }


        else if(m.equals("Windows")){
            holder.s.setImageResource(R.drawable.m);

        }


        else if(m.equals("Entertainment")){
            holder.s.setImageResource(R.drawable.n);

        }
        else if(m.equals("Android")){
            holder.s.setImageResource(R.drawable.l);

        }
        else{
            holder.s.setImageResource(a);
        }





        holder.tt.setText(statusobj.getString("date"));

        String s=statusobj.getString("topic_cat");
        holder.topic_cat.setText(" -->>" +s );
        return converterView;
    }
}
