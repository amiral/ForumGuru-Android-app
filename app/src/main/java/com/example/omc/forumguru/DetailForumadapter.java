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

import java.util.List;
public class DetailForumadapter extends ArrayAdapter<ParseObject> {

    protected Context mcontext;
    protected List<ParseObject> mreply;


    public DetailForumadapter(Context context,List<ParseObject> status) {
        super(context,R.layout.forumdetail_item,status);
        mcontext=context;
        mreply=status;
    }

    public static class ViewHolder{
        TextView r_by;
        TextView reply,p;
        ImageView pic;
    }

    public View getView(final int position, View converterView, ViewGroup parent){
        final ViewHolder holder;
        if(converterView==null){
            converterView= LayoutInflater.from(mcontext).inflate(R.layout.forumdetail_item,null);
            holder=new ViewHolder();
            holder.pic=(ImageView)converterView.findViewById(R.id.f_pic);
            holder.r_by=(TextView)converterView.findViewById(R.id.r_by);
            holder.p=(TextView)converterView.findViewById(R.id.tmb);

            holder.reply=(TextView)converterView.findViewById(R.id.r);
            converterView.setTag(holder);

        }
        else{
            holder=(ViewHolder)converterView.getTag();
        }

        ParseObject statusobj=mreply.get(position);
        //title
        String username=statusobj.getString("by");
        holder.r_by.setText(username);
        //content
        String sta=statusobj.getString("reply_content");
        holder.reply.setText(sta);

        String objid=statusobj.getString("userid");
        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.getInBackground(objid, new GetCallback<ParseUser>() {
            public void done(ParseUser object, ParseException e) {

                Picasso.with(mcontext).
                        load(object.getParseFile("profilepic").getUrl()).
                        noFade().into(holder.pic);

            }
        });

        holder.p.setText(statusobj.getString("date"));

        return converterView;
    }
}
