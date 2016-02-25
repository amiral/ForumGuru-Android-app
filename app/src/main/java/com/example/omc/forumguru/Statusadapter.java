package com.example.omc.forumguru;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;public class Statusadapter extends ArrayAdapter<ParseObject> {

    protected Context mcontext;
    protected List<ParseObject> mstatus;


    public Statusadapter(Context context,List<ParseObject> status) {
        super(context,R.layout.homepagelayout,status);
        mcontext=context;
        mstatus=status;
    }

    public static class ViewHolder{
        TextView u_name;
        TextView comment;
        TextView date,detail;
        ImageView user,pic;
    }

    public View getView(final int position, View converterView, ViewGroup parent){
        final ViewHolder holder;
        if(converterView==null){
            converterView= LayoutInflater.from(mcontext).inflate(R.layout.homepagelayout,null);
            holder=new ViewHolder();

            holder.u_name=(TextView)converterView.findViewById(R.id.tv11);
            holder.detail=(TextView)converterView.findViewById(R.id.tv123);
            holder.date=(TextView)converterView.findViewById(R.id.tv1234);

            holder.comment=(TextView)converterView.findViewById(R.id.tv12);
            holder.user=(ImageView)converterView.findViewById(R.id.p_user);
            holder.pic=(ImageView)converterView.findViewById(R.id.p_pic);

            converterView.setTag(holder);

        }
        else{
            holder=(ViewHolder)converterView.getTag();
        }

        ParseObject statusobj=mstatus.get(position);
        //title
        String username=statusobj.getString("statusby");
        holder.u_name.setText(username);
        //content
        String sta=statusobj.getString("status");
        holder.comment.setText(sta);

        String date=statusobj.getString("date");
        holder.date.setText(date);
        String detail=statusobj.getString("detail");
        holder.detail.setText("    "  + detail);

        String objid=statusobj.getString("userid");
        Picasso.with(mcontext).
                load(statusobj.getParseFile("image").getUrl()).
                noFade().into(holder.pic);

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.getInBackground(objid, new GetCallback<ParseUser>() {
            public void done(ParseUser object, ParseException e) {

                Picasso.with(mcontext).
                        load(object.getParseFile("profilepic").getUrl()).
                        noFade().into(holder.user);

            }
        });



        return converterView;
    }
}
