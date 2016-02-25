package com.example.omc.forumguru;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class Forumadaptera extends ArrayAdapter<ParseObject> {

    protected Context mcontext;
    protected List<ParseObject> mtopic;

    private Date date;
    public Forumadaptera(Context context,List<ParseObject> status) {
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

        String objid=statusobj.getString("userid");

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.getInBackground(objid, new GetCallback<ParseUser>() {
            public void done(ParseUser object, ParseException e) {

                Picasso.with(mcontext).
                        load(object.getParseFile("profilepic").getUrl()).
                        noFade().into(holder.s);

            }
        });



        holder.tt.setText(statusobj.getString("date"));

        String s=statusobj.getString("topic_cat");
        holder.topic_cat.setText(" -->>" +s );
        return converterView;
    }
}
