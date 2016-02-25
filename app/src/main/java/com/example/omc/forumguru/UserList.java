package com.example.omc.forumguru;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserList extends CustomActivity
{

    private ArrayList<ParseUser> uList;

    public static ParseUser user=ParseUser.getCurrentUser();
    public static EditText text;
    public static Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.user_list, frameLayout);
        mDrawerList.setItemChecked(position, true);
        text=(EditText)findViewById(R.id.search);
        btn=(Button)findViewById(R.id.btnsrc);
        if(user==null){
            startActivity(new Intent(UserList.this, Index.class));
        }


onCk();
        getnotification();

    }

    public void onClick(View v){

        ParseUser.getQuery()
                .whereNotEqualTo("username", user.getUsername()).whereContains("username",text.getText().toString())

                .findInBackground(new FindCallback<ParseUser>() {

                    @Override
                    public void done(List<ParseUser> li, ParseException e) {

                        if (li != null) {
                            if (li.size() == 0)
                                Toast.makeText(UserList.this,
                                        R.string.msg_no_user_found,
                                        Toast.LENGTH_SHORT).show();

                            uList = new ArrayList<ParseUser>(li);
                            ListView list = (ListView) findViewById(R.id.list);
                            list.setAdapter(new UserAdapter());
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> arg0,
                                                        View arg1, int pos, long arg3) {
                                    startActivity(new Intent(UserList.this,
                                            Chat.class).putExtra(
                                            Const.EXTRA_DATA, uList.get(pos)
                                                    .getUsername()));
                                }
                            });
                        } else {
                            Utils.showDialog(
                                    UserList.this,
                                    getString(R.string.err_users) + " "
                                            + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });

    }

    public void onCk(){

ParseUser m=ParseUser.getCurrentUser();

            ParseUser.getQuery()
                    .whereNotEqualTo("username",m.getUsername())

            .findInBackground(new FindCallback<ParseUser>() {

                @Override
                public void done(List<ParseUser> li, ParseException e) {

                    if (li != null) {
                        if (li.size() == 0)
                            Toast.makeText(UserList.this,
                                    R.string.msg_no_user_found,
                                    Toast.LENGTH_SHORT).show();

                        uList = new ArrayList<ParseUser>(li);
                        ListView list = (ListView) findViewById(R.id.list);
                        list.setAdapter(new UserAdapter());
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0,
                                                    View arg1, int pos, long arg3) {
                                startActivity(new Intent(UserList.this,
                                        Chat.class).putExtra(
                                        Const.EXTRA_DATA, uList.get(pos)
                                                .getUsername()));
                            }
                        });
                    } else {
                        Utils.showDialog(
                                UserList.this,
                                getString(R.string.err_users) + " "
                                        + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){

            case R.id.action_logout:

                updateUserStatus(false);
                Intent i=new Intent(UserList.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    private void updateUserStatus(boolean online)
    {
        user.put("online", online);
        user.saveEventually();
    }

    public void getnotification(){
        ParseUser m=ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("temp");
        query.whereEqualTo("receiver", m.getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(java.util.List<ParseObject> status, ParseException e) {
                if (e == null) {
                    String mes,by;
                    int m=status.size();
                    if(m!=0) {
                        for (int i = 0; i < m; i++) {
                            Intent k=new Intent();
                            final PendingIntent p=PendingIntent.getActivity(UserList.this, 0, k, 0);
                            mes = status.get(i).getString("message");
                            by = status.get(i).getString("sender");
                            String id=status.get(i).getObjectId();
                            Notification n = new Notification.Builder(UserList.this)
                                    .setContentTitle(by)
                                    .setTicker("message")
                                    .setContentIntent(p)
                                    .setContentText(mes)
                                    .setSmallIcon(R.drawable.ic_launcher)
                                    .getNotification();
                            n.flags = Notification.FLAG_AUTO_CANCEL;
                            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(i, n);
                            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("temp");
                            query.whereEqualTo("objectId",id);
                            query.getFirstInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject s, ParseException e) {
                                    try {
                                        s.delete();
                                        s.saveInBackground();
                                    } catch (ParseException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                        }

                    }
                }
                else {

                }


            }
        });


    }
    protected void onResume()
    {
        super.onResume();

ParseUser m=ParseUser.getCurrentUser();
        if(m==null) {
            startActivity(new Intent(UserList.this, Index.class));
        }

    }



    private class UserAdapter extends BaseAdapter
    {
        private String f;


        @Override
        public int getCount()
        {
            return uList.size();
        }

        @Override
        public ParseUser getItem(int arg0)
        {
            return uList.get(arg0);
        }

        @Override
        public long getItemId(int arg0)
        {
            return arg0;
        }

        @Override
        public View getView(int pos, View v, ViewGroup arg2)
        {
            if (v == null)
                v = getLayoutInflater().inflate(R.layout.chat_item, null);

            ParseUser c = getItem(pos);

            TextView lbl = (TextView) v.findViewById(R.id.tv1);
           ImageView mPreviewImageView=(ImageView)v.findViewById(R.id.p_pic);

            Picasso.with(UserList.this).
                    load(c.getParseFile("profilepic").getUrl()).
                    noFade().into(mPreviewImageView);

            TextView lb2 = (TextView) v.findViewById(R.id.tv2);
            ImageView s=(ImageView) v.findViewById(R.id.a);
            String sta=c.getString("status");

            lb2.setText("status : "+ sta);



            lbl.setText("  " + c.getUsername());

            if(c.getBoolean("online"))
            {s.setImageResource(R.drawable.ic_online);}
            else {
                s.setImageResource(R.drawable.ic_offline);
            }
            return v;
        }



    }
}
