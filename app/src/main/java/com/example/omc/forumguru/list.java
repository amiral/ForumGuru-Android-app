package com.example.omc.forumguru;

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
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class list extends ArrayAdapter<ParseObject> {

    protected Context mcontext;
    protected List<ParseObject> mtopic;

    private Date date;
    public list(Context context,List<ParseObject> status) {
        super(context,R.layout.chat_item,status);
        mcontext=context;
        mtopic=status;
    }

    public static class ViewHolder{

        TextView lbl;
        ImageView mPreviewImageView,m;
       TextView lb2;

    }




    public View getView(final int position, View converterView, ViewGroup parent){
        final ViewHolder holder;

        if(converterView==null){
            converterView= LayoutInflater.from(mcontext).inflate(R.layout.chat_item,null);
            holder=new ViewHolder();
            holder.mPreviewImageView=(ImageView)converterView.findViewById(R.id.p_pic);
     holder.lbl=(TextView)converterView.findViewById(R.id.tv1);
            holder.m=(ImageView)converterView.findViewById(R.id.a);
            holder.lb2=(TextView)converterView.findViewById(R.id.tv2);
            converterView.setTag(holder);

        }
        else{
            holder=(ViewHolder)converterView.getTag();
        }


        ParseObject statusobj=mtopic.get(position);
        holder.lb2.setText("status : "+ statusobj.getString("status"));
        holder.lbl.setText(statusobj.getString("username"));
        Picasso.with(getContext()).
                load(statusobj.getParseFile("profilepic").getUrl()).
                noFade().into(holder.mPreviewImageView);
        if(statusobj.getBoolean("online"))
        {
            holder.m.setImageResource(R.drawable.ic_online);}
        else {
            holder.m.setImageResource(R.drawable.ic_offline);
        }

        return converterView;
    }
}
